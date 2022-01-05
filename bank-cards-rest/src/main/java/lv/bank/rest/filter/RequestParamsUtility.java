package lv.bank.rest.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RequestParamsUtility {

    private static final ThreadLocal<RequestParams> threadLocal = new ThreadLocal<>();

    public static void setRequestParams(String country, String personalId, String cif, String requestId, String deviceId, String walletId) {
        threadLocal.set(new RequestParams(country, personalId, cif, requestId, deviceId, walletId));
    }

    public static String getCountry() {
        RequestParams params = getMap();
        return params == null ? null : params.getCountry();
    }

    public static String getRequestId() {
        RequestParams params = getMap();
        return params == null ? null : params.getRequestId();
    }

    public static String getPersonalId() {
        RequestParams params = getMap();
        return params == null ? null : params.getPersonalId();
    }

    public static String getDeviceId() {
        RequestParams params = getMap();
        return params == null ? null : params.getDeviceId();
    }

    public static String getWalletId() {
        RequestParams params = getMap();
        return params == null ? null : params.getWalletId();
    }

    public static String getCif() {
        RequestParams params = getMap();
        return params == null ? null : params.getCif();
    }

    private static RequestParams getMap() {
        return threadLocal.get();
    }

    @RequiredArgsConstructor
    @Getter
    public static class RequestParams {

        private final String country;
        private final String personalId;
        private final String cif;
        private final String requestId;
        private final String deviceId;
        private final String walletId;

    }
}
