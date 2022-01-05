package lv.bank.cards.base.utils;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.utils.LinkAppProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

@Startup
@Singleton
@Slf4j
public class StatisticCounter {

    private static final Logger ZABBIX = Logger.getLogger("ZABBIX");

    private static final String ZABBIX_STATS_PREFIX = "Zabbix Stats";
    private static final String REQUESTS = "requests";
    private static final String ERRORS = "errors";

    static ReentrantLock counterLock = new ReentrantLock(true); // enable fairness policy

    private static final AtomicLong TOTAL_TIME = new AtomicLong(0);
    private static final AtomicLong TOTAL_COUNT = new AtomicLong(0);

    private static final Set<GroupStatistics> groups = new HashSet<>();
    private static final GroupStatistics other = new GroupStatistics("other", null);

    @PostConstruct
    public void init() {
        LinkAppProperties.getSonicHost(); // Just to init LinkApp properties
        sendStatistics();
    }

    public static void countRequest(String ipAddress) {
        counterLock.lock();
        // Always good practice to enclose locks in a try-finally block
        try {
            TOTAL_COUNT.getAndIncrement();
            GroupStatistics group = getGroup(ipAddress);
            group.incrementRequests();
        } finally {
            counterLock.unlock();
        }
    }

    public static void countResponse(String ipAddress, long time, boolean error) {
        counterLock.lock();
        // Always good practice to enclose locks in a try-finally block
        try {
            TOTAL_TIME.addAndGet(time);
            if (error) {
                GroupStatistics group = getGroup(ipAddress);
                group.incrementErrors();
            }
        } finally {
            counterLock.unlock();
        }
    }

    @Schedule(hour = "*", minute = "*/1", second = "1", persistent = false)
    public void sendStatistics() {
        counterLock.lock();
        // Always good practice to enclose locks in a try-finally block
        try {
            String message = ZABBIX_STATS_PREFIX + " - {total} " + REQUESTS + ": " + TOTAL_COUNT.get();
            log.info(message);
            ZABBIX.error(message);
            message = ZABBIX_STATS_PREFIX + " - {average_time} " + REQUESTS + ": " + (TOTAL_COUNT.get() == 0 ? "0" : (TOTAL_TIME.get() / TOTAL_COUNT.get()));
            log.info(message);
            ZABBIX.error(message);
            for (GroupStatistics group : groups) {
                message = ZABBIX_STATS_PREFIX + " - {" + group.getName() + "} " + REQUESTS + ": " + group.getAndResetRequests();
                log.info(message);
                ZABBIX.error(message);
                message = ZABBIX_STATS_PREFIX + " - {" + group.getName() + "} " + ERRORS + ": " + group.getAndResetErrors();
                log.info(message);
                ZABBIX.error(message);
            }
            message = ZABBIX_STATS_PREFIX + " - {" + other.getName() + "} " + REQUESTS + ": " + other.getAndResetRequests();
            log.info(message);
            ZABBIX.error(message);
            message = ZABBIX_STATS_PREFIX + " - {" + other.getName() + "} " + ERRORS + ": " + other.getAndResetErrors();
            log.info(message);
            ZABBIX.error(message);
            TOTAL_COUNT.set(0L);
            TOTAL_TIME.set(0L);
            if (LinkAppProperties.REREAD_GROUPS.get()) {
                // Reread groups
                groups.clear();
                for (Object o : LinkAppProperties.PROPERTIES.keySet()) {
                    if (o instanceof String) {
                        String key = (String) o;
                        if (StringUtils.startsWith(key, LinkAppProperties.STATISTICS_GROUP_PREFIX)) {
                            groups.add(
                                    new GroupStatistics(StringUtils.substringAfter(key,
                                            LinkAppProperties.STATISTICS_GROUP_PREFIX),
                                            LinkAppProperties.getLinkAppProperties(key)));
                        }
                    }
                }
                LinkAppProperties.REREAD_GROUPS.set(false);
            }
        } finally {
            counterLock.unlock();
        }
    }

    private static GroupStatistics getGroup(String ipAddress) {
        String ipAddressFromHostname = getIPFromHostname(ipAddress);
        for (GroupStatistics group : groups) {
            if (Pattern.matches(group.getRegEx(), ipAddressFromHostname)) {
                return group;
            }
        }
        return other;
    }

    private static String getIPFromHostname(String hostname) {
        if (!Character.isDigit(hostname.toCharArray()[0])) {
            try {
                return InetAddress.getByName(hostname).getHostAddress();
            } catch (Exception ignored) {
            }
        }
        return hostname;
    }
}
