package com.base.utils;


public class MathUtil {

    //正切化为正弦
    public static double sin(double tan) {
        return tan / Math.sqrt(1 + Math.pow(tan, 2));
    }

    //正切化为余弦
    public static double cosin(double tan) {
        return 1 / Math.sqrt(1 + Math.pow(tan, 2));
    }

    /**
     * * 该坐标系为右下为第一象限，逆时针分别为1,2,3,4 象限
     * <p>  求点xxx的坐标
     * 该点位于圆的半径延长线上，该点到圆心的距离为strokeWidth
     * 已知原点坐标和一个弧线上的点坐标和正切值，求延长线的坐标,
     *
     * @param tan         弧线的斜率坐标
     * @param strokeWidth 该点到圆心的距离
     * @return 延长线顶点的坐标
     */
    //tan 是 tangent 的缩写，即常见的正切， 其中tan0 x，tan1是y
    //倾斜角为互补角时,斜率互为负倒数
    public static float[] getCoordinate(float tan[], float strokeWidth) {
        float x = strokeWidth * tan[1];
        float y = strokeWidth * tan[0];
        y = -y;
        return new float[]{x, y};
    }

    //tan 是 tangent 的缩写，即常见的正切， 其中tan0 x，tan1是y
    //倾斜角为互补角时,斜率互为负倒数
    public static float[] slopeChage(float tan[]) {
        float x = tan[1];
        float y = tan[0];
        y = -y;
        return new float[]{x, y};
    }


    /**
     * <p>  求点xxx的坐标
     * 已知两点,起点和终点，距离起点为strokeWidth的坐标
     *
     * @param origin      起点坐标
     * @param pos         终点坐标
     * @param strokeWidth 距起点的距离
     * @return 延长线顶点的坐标
     */
    public static float[] getCoordinate(float origin[], float pos[], float strokeWidth) {
        //Math.atan2  计算出来是一个弧度值，转换成角度必须乘以  180/PI、
        //  double angle = Math.atan2(tan[1], tan[0]) * 180 / Math.PI;

        double tans = (pos[1] - origin[1]) / (pos[0] - origin[0]);
        float x = (float) Math.abs(cosin(tans)) * strokeWidth;
        float y = (float) Math.abs(sin(tans)) * strokeWidth;

        if (pos[0] - origin[0] > 0) {
            x = origin[0] + x;
        } else {
            x = origin[0] - x;
        }

        if (pos[1] - origin[1] > 0) {
            y = origin[1] + y;
        } else {
            y = origin[1] - y;
        }
        return new float[]{x, y};
    }


}
