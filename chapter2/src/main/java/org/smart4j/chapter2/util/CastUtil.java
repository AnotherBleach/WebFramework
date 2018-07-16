package org.smart4j.chapter2.util;



public class CastUtil{

    public static String castString(Object object) {

        return castString(object, "");


    }

    public static String castString(Object object, String s) {

        String result = s;
        if (object != null) {
            s = String.valueOf(object);

        }
        return s;

    }

    public static double castDouble(Object object) {

        return castDouble(object, 0);


    }

    public static double castDouble(Object object, double i) {

        double result = i;
        if (object != null) {

            String value = castString(object);
            if (StringUtils.isNotEmpty(value)) {

                try {
                    result = Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    result = i;
                    e.printStackTrace();
                }

            }


        }
        return result;


    }


    public static long castLong(Object object) {

        return castLong(object, 0);


    }

    public static long castLong(Object object, long i) {


        long result = i;
        if (object != null) {

            String value = castString(object);
            if (StringUtils.isNotEmpty(value)) {

                try {
                    result = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    result = i;
                    e.printStackTrace();
                }

            }


        }
        return result;

    }



    public static int castInt(Object object) {

        return castInt(object, 0);


    }

    public static int castInt(Object object, int i) {

        int result = i;
        if (object != null) {

            String value = castString(object);
            if (StringUtils.isNotEmpty(value)) {

                try {
                    result = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    result = i;
                    e.printStackTrace();
                }

            }


        }
        return result;


    }

    public static boolean castBoolean(Object object)
    {

        return castBoolean(object,false);

    }

    private static boolean castBoolean(Object object, boolean b) {
        boolean result = b;
        if (object != null) {

            String value = castString(object);
            if (StringUtils.isNotEmpty(value)) {

                try {
                    result = Boolean.parseBoolean(value);
                } catch (NumberFormatException e) {
                    result = b;
                    e.printStackTrace();
                }

            }


        }
        return result;

    }


}
