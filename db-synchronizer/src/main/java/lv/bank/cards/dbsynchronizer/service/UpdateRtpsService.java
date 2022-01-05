package lv.bank.cards.dbsynchronizer.service;

import lv.bank.cards.core.entity.linkApp.PcdAccumulator;
import lv.bank.cards.core.entity.linkApp.PcdAccumulatorPK;
import lv.bank.cards.core.entity.linkApp.PcdRiskLevel;
import lv.bank.cards.core.entity.linkApp.PcdRiskLevelPK;
import lv.bank.cards.core.entity.rtps.StipParamList;
import lv.bank.cards.core.entity.rtps.StipParamListPK;
import lv.bank.cards.core.entity.rtps.StipParamN;
import lv.bank.cards.core.entity.rtps.StipParamNPK;
import lv.bank.cards.dbsynchronizer.dao.LinkAppDao;
import lv.bank.cards.dbsynchronizer.dao.RtpsDao;
import lv.bank.cards.dbsynchronizer.utils.AbstractSpecialConverter;
import lv.bank.cards.dbsynchronizer.utils.AbstractSpecialUpdateHandler;
import lv.bank.cards.dbsynchronizer.utils.Converter;
import lv.bank.cards.dbsynchronizer.utils.DataAndStatelessSessionHolder;
import lv.bank.cards.dbsynchronizer.utils.MonitorRiskLevel;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateRtpsService {

    private static final Logger log = Logger.getLogger(UpdateRtpsService.class);
    private static final Logger ACCUMULATOR_TYPES = Logger.getLogger("accumulator-types");
    private static final Logger ACCUMULATOR_MANDATORY = Logger.getLogger("mandatory-accumulator");
    private static boolean hasMissingAccumulators = false;
    protected final RtpsDao rtpsDao;
    protected final LinkAppDao linkAppDao;
    protected Map<String, AbstractSpecialUpdateHandler> handlers;

    public UpdateRtpsService(RtpsDao rtpsDao, LinkAppDao linkAppDao) {
        this.rtpsDao = rtpsDao;
        this.linkAppDao = linkAppDao;
        this.handlers = getAvailableHandlers();

        Converter.defConverter(new StipParamListPcdAccumulatorConverter());
        Converter.defConverter(new StipParamNPcdRiskLevelConverter());
    }

    protected Map<String, AbstractSpecialUpdateHandler> getAvailableHandlers() {
        Map<String, AbstractSpecialUpdateHandler> handlers = new HashMap<>();
        handlers.put(PcdAccumulator.class.getName(), new PcdAccumulatorUpdateHandler());
        return handlers;
    }

    public void updateDb(Date prevUpdWaterMark, Date curWaterMark) throws NoSuchMethodException {
        long startTime = System.currentTimeMillis();
        log.info("updateDb RTPS START");
        update(StipParamList.class, PcdAccumulator.class, PcdAccumulator.class.getMethod("getId"), prevUpdWaterMark, curWaterMark);
        update(StipParamN.class, PcdRiskLevel.class, PcdRiskLevel.class.getMethod("getId"), prevUpdWaterMark, curWaterMark);
        log.info("RTPS PROCESSING DONE");

        List<MonitorRiskLevel> list = linkAppDao.getMonitorRiskLevelList();
        if (list.isEmpty()) {
            ACCUMULATOR_MANDATORY.info("OK");
        } else {
            for (MonitorRiskLevel level : list) {
                ACCUMULATOR_MANDATORY.info(String.format("Risk Level [%s], missing accumulator [%s]", level.getParam_grp(), level.getDescription()));
            }
        }

        if (!hasMissingAccumulators) {
            ACCUMULATOR_TYPES.info("OK");
        }
        long totalTime = (System.currentTimeMillis() - startTime)/1000;
        log.info("updateDb RTPS - END, time of execution is = " + totalTime + " seconds");
    }

    protected void update(Class<?> s, Class<?> d, Method getIdMethod, Date lastUpdate, Date currentLevel) {
        log.info("PROCESSING update from " + s.getSimpleName() + " to " + d.getSimpleName());
        DataAndStatelessSessionHolder<ScrollableResults> result = rtpsDao.findDataToUpdate(s, d, lastUpdate, currentLevel);
        linkAppDao.processUpdate(result.getData(), d, getIdMethod, getAvailableHandlers());
        if (result.getStatelessSession() != null){
            result.getStatelessSession().close();
        }
    }

    public static class StipParamNPcdRiskLevelConverter extends AbstractSpecialConverter {

        public StipParamNPcdRiskLevelConverter() {
            super();
            ConvertUtils.register(new StipParamNPKPcdRiskLevelPKConverter(), PcdRiskLevelPK.class);
        }

        public Class<?> getSourceClass() {
            return StipParamN.class;
        }

        public Class<?> getDestinationClass() {
            return PcdRiskLevel.class;
        }

        static class StipParamNPKPcdRiskLevelPKConverter implements org.apache.commons.beanutils.Converter {

            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof StipParamNPK) {
                    StipParamNPK pk = ((StipParamNPK) arg1);
                    return new PcdRiskLevelPK(pk.getParamGrp(), pk.getCentreId());
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdRiskLevelPK");
                }
            }
        }
    }

    public static class StipParamListPcdAccumulatorConverter extends AbstractSpecialConverter {

        public StipParamListPcdAccumulatorConverter() {
            super();
            ConvertUtils.register(new StipParamListPKPcdAccumulatorPKConverter(), PcdAccumulatorPK.class);
        }

        public Class<?> getSourceClass() {
            return StipParamList.class;
        }

        public Class<?> getDestinationClass() {
            return PcdAccumulator.class;
        }

        static class StipParamListPKPcdAccumulatorPKConverter implements org.apache.commons.beanutils.Converter {

            @SuppressWarnings({"rawtypes", "unchecked"})
            public Object convert(Class arg0, Object arg1) {
                if (arg1 == null) return null;
                if (arg1 instanceof StipParamListPK) {
                    StipParamListPK pk = ((StipParamListPK) arg1);
                    return new PcdAccumulatorPK(pk.getAccumId(), pk.getCentreId());
                } else {
                    throw new ConversionException("Can't convert " + arg1.getClass() + " to PcdAccumulatorPK");
                }
            }
        }
    }

    public class PcdAccumulatorUpdateHandler extends AbstractSpecialUpdateHandler {
        @Override
        public void afterInsertFilter(Object o) {
            // Check description in PcdAccumulatorType
            PcdAccumulator level = (PcdAccumulator) o;
            if (level.getParamGrp().length() == 3) {
                Long count = linkAppDao.countPcdAccumulatorTypeByDescription(level.getDescription());
                log.info("Found " + count + " of " + level.getDescription());
                if (count == 0L) {
                    hasMissingAccumulators = true;
                    ACCUMULATOR_TYPES.info(String.format("Risk Level [%s], accumulator Id [%s], description [%s]",
                            level.getParamGrp(), level.getId().getAccumId(), level.getDescription()));
                }
            }
        }
    }

}
