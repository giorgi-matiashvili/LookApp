package com.lookapp.storage;

/**
 * Created by Giorgi on 6/20/2015.
 */

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.reflect.TypeToken;
import com.lookapp.App;
import com.lookapp.utils.AppLogger;
import com.lookapp.utils.GsonUtils;
import com.lookapp.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by giorgi-matiashvili on 2/6/2015.
 */
public class DataStorage {

    private static AppLogger logger = AppLogger.getLogger(DataStorage.class);

    private static final File APP_DIR = App.getInstance().getFilesDir();

    private static final String BANNERS_PATH = "banners";
    private static final String IMAGES_PATH = "avatars";
    private static final String OBJECT_PATH = "objects";


    public static byte[] getImage(String imageId) throws IOException {
        return read(IMAGES_PATH, imageId);
    }

    public static void storeImage(String name, byte[] images) throws IOException {
        store(IMAGES_PATH, name, images);
    }

    public static void storeImage(String name, byte[] images, boolean overwrite) throws IOException {
        store(IMAGES_PATH, name, images, overwrite);
    }

    public static byte[] read(String fileName) throws IOException {
        return read(null, fileName);
    }

    public static byte[] read(String path, String fileName) throws IOException {
        FileInputStream inp = null;
        try {
            inp = new FileInputStream(new File(getDir(path), fileName));
            return IOUtils.toByteArray(inp);
        } finally {
            IOUtils.closeQuietly(inp);
        }
    }

    public static void store(String path, String name, byte[] data) throws IOException {
        store(path, name, data, true);
    }


    public static void store(String path, String name, byte[] data, boolean overwrite) throws IOException {
        FileOutputStream fos = null;
        try {
            File file = new File(getDir(path), name);
            if (file.exists() && !overwrite) {
                return;
            }
            fos = new FileOutputStream(file);
            fos.write(data);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }


    public static boolean imageExists(String name) {
        File file = new File(getDir(IMAGES_PATH), name);
        if (file.exists())
            return true;
        return false;
    }

    public static <T> T getObject(String key, Type type) {
        try {
            String valString = new String(read(OBJECT_PATH, key));
            logger.d("getting object from file. file = " + key);
            return GsonUtils.fromJson(valString, type);
        } catch (Exception e) {
            logger.e("Error While Reading Object. name: " + key + ". errorMessage: " + e.toString());
        }
        return null;
    }

    public static void storeObject(String name, Object obj) throws IOException {
        String valueString = GsonUtils.toJson(obj);
        store(OBJECT_PATH, name, valueString.getBytes());
    }


//    public static List<AdBannerInfo> getBannerList() {
//        List<AdBannerInfo> banners = getObject(Consts.AD_BANNER_INFOS_STORAGE, new TypeToken<List<AdBannerInfo>>() {
//        }.getType());
//        banners = (banners == null ? new ArrayList<AdBannerInfo>() : banners);
//        for (Iterator<AdBannerInfo> iterator = banners.iterator(); iterator.hasNext(); ) {
//            AdBannerInfo bannerInfo = iterator.next();
//            // read image of this banner
//            try {
//                bannerInfo.setImgData(read(BANNERS_PATH, bannerInfo.getImageId()));
//            } catch (IOException e) {
//                // if we don't have image of this banner, then remove it from list
//                iterator.remove();
//            }
//        }
//        return banners;
//    }

//    public static void storeBannerList(List<AdBannerInfo> banners) throws IOException {
//        storeObject(Consts.AD_BANNER_INFOS_STORAGE, banners);
//        for (AdBannerInfo bannerInfo : banners) {
//            try {
//                store(BANNERS_PATH, bannerInfo.getImageId(), bannerInfo.getImgData(), false);
//            } catch (IOException e) {
//                logger.e("Cant store banner image id: " + bannerInfo.getImageId());
//            }
//        }
//    }

    public static void deleteBannerImages(Set<String> imageIds) {
        File dir = getDir(BANNERS_PATH);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                if (imageIds.contains(children[i])) {
                    new File(dir, children[i]).delete();
                }
            }
        }
    }


    private static File getDir(String path) {
        File dir;
        if (path == null) {
            dir = APP_DIR;
        } else {
            dir = new File(APP_DIR, path);
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        return dir;
    }


}