//package com.lookapp.transport;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.lang.reflect.Type;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import android.util.Log;
//
//import com.google.gson.Gson;
//
//public class LookAppTransport {
//
//    private static final int HTTP_MOBILE_BANK_ERROR = 505;
//    private static final int HTTP_STATUS_OK = 200;
//    private static final int STATUS_SESSION_EXPIRED = 15;
//
//    private static final String BASE_URL = "http://185.41.156.21:60000/api/MobileBank";
//    //    private static final String BASE_URL = "https://www.ebanking.ge:6000/api/MobileBank/";
//    private static final String ENCODING = "UTF-8";
//
//
//    private Gson gson;
//
//
//    public LookAppTransport() {
//        this.gson = new Gson();
//    }
//
//    private void setDefaultParams(HttpURLConnection connection) {
//        connection.setConnectTimeout(15 * 1000);
//    }
//
//
//    public <T> T execute(String operationName, Object input, Type returnType) throws MobileBankException {
//        InputStream is = null;
//        T result = null;
//        try {
//            HttpURLConnection conn = executeOperation(operationName, input);
//
//            int statusCode = conn.getResponseCode();
//            String responseString;
//            if (statusCode == HTTP_STATUS_OK) {
//                is = conn.getInputStream();
//                responseString = IOUtils.toString(is, ENCODING);
//                result = gson.fromJson(responseString, returnType);
//            } else {
//                is = conn.getErrorStream();
//                responseString = IOUtils.toString(is, ENCODING);
//                handleErrorStatusCode(statusCode, responseString);
//            }
//
//            logger.i("Got response from server. operationName: " + operationName + "; statusCode: " + statusCode + "; response: " + responseString);
//        } catch (Exception e) {
//            logger.e("MobileBankTransport:execute error", e);
//            handleException(e);
//        } finally {
//            IOUtils.closeQuietly(is);
//        }
//        return result;
//    }
//
//
//    private HttpURLConnection executeOperation(String operationName, Object input) throws IOException {
//        URL url = new URL(BASE_URL);
//        HttpURLConnection post = (HttpURLConnection) url.openConnection();
//        setDefaultParams(post);
//        post.setDoInput(true);
//        post.setDoOutput(true);
//        post.setRequestMethod("POST");
//        post.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//        post.setRequestProperty("operationName", operationName);
//        post.setRequestProperty("lang", Settings.getLanguage().toString());
//
//        String postData;
//        if (input != null) {
//            postData = gson.toJson(input);
//            logger.d("Operation Name: " + operationName + " Request: " + postData);
//            byte[] outputInBytes = postData.getBytes(ENCODING);
//            OutputStream os = post.getOutputStream();
//            os.write(outputInBytes);
//            os.close();
//            os.close();
//        }
//
//        return post;
//    }
//
//    private void handleErrorStatusCode(int statusCode, String errorData) throws MobileBankException {
//        Log.d("errorData", errorData);
//
//        if (statusCode == HTTP_MOBILE_BANK_ERROR) {
//            MobileBankError error = gson.fromJson(errorData, MobileBankError.class);
//            MobileBankException ex = new MobileBankException(error);
//
//            if (ex.getLookAppError().getStatusCode() == STATUS_SESSION_EXPIRED) { //handle sign out
//                App.getInstance().sendSessionTimeoutIntent();
//            }
//
//            throw ex;
//        } else if (statusCode != HTTP_STATUS_OK) {
//            throw new MobileBankException(errorData);
//        }
//    }
//
//    private void handleException(Exception e) throws MobileBankException {
//        if (e instanceof MobileBankException) {
//            throw (MobileBankException) e;
//        } else {
//            throw new MobileBankException(e);
//        }
//    }
//
//
//    public byte[] getBinaryData(String operationName, Object input) throws MobileBankException {
//        byte[] result = null;
//        InputStream is = null;
//        try {
//            HttpURLConnection conn = executeOperation(operationName, input);
//            int statusCode = conn.getResponseCode();
//            logger.i("Got response from server. operationName:" + operationName + "; statusCode: " + statusCode);
//
//            if (statusCode == HTTP_STATUS_OK) {
//                is = conn.getInputStream();
//                result = IOUtils.toByteArray(is);
//            } else {
//                is = conn.getErrorStream();
//                handleErrorStatusCode(statusCode, IOUtils.toString(is));
//            }
//        } catch (Exception e) {
//            logger.e("MobileBakTransport:getBinaryData error", e);
//            handleException(e);
//        } finally {
//            IOUtils.closeQuietly(is);
//        }
//        return result;
//    }
//}