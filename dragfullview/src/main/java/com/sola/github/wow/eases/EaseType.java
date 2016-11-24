package com.sola.github.wow.eases;


import android.graphics.PointF;

public enum EaseType {

    EaseInSine(EaseInSine.class), //0
    EaseOutSine(EaseOutSine.class),
    EaseInOutSine(EaseInOutSine.class),

    EaseInQuad(EaseInQuad.class),// 3
    EaseOutQuad(EaseOutQuad.class),
    EaseInOutQuad(EaseInOutQuad.class),

    EaseInCubic(EaseInCubic.class),// 6
    EaseOutCubic(EaseOutCubic.class),
    EaseInOutCubic(EaseInOutCubic.class),

    EaseInQuart(EaseInQuart.class),// 9
    EaseOutQuart(EaseOutQuart.class),
    EaseInOutQuart(EaseInOutQuart.class),

    EaseInQuint(EaseInQuint.class),// 12
    EaseOutQuint(EaseOutQuint.class),
    EaseInOutQuint(EaseInOutQuint.class),

    EaseInExpo(EaseInExpo.class),// 15
    EaseOutExpo(EaseOutExpo.class),
    EaseInOutExpo(EaseInOutExpo.class),

    EaseInCirc(EaseInCirc.class),// 18
    EaseOutCirc(EaseOutCirc.class),
    EaseInOutCirc(EaseInOutCirc.class),

    EaseInBack(EaseInBack.class),// 21
    EaseOutBack(EaseOutBack.class),
    EaseInOutBack(EaseInOutBack.class),

    EaseInElastic(EaseInElastic.class),// 24
    EaseOutElastic(EaseOutElastic.class),
    EaseInOutElastic(EaseInOutElastic.class),

    EaseInBounce(EaseInBounce.class), // 27
    EaseOutBounce(EaseOutBounce.class),
    EaseInOutBounce(EaseInOutBounce.class),

    AntiLinear(AntiLinear.class),// 30

    Linear(Linear.class);

    private Class easingType;

    /**
     * ease animation helps to make the movement more real
     *
     * @param easingType
     */
    EaseType(Class easingType) {
        this.easingType = easingType;
    }

    public float getOffset(float offset) {
        try {
            return ((CubicBezier) easingType.getConstructor().newInstance()).getOffset(offset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("CubicBezier init error.");
        }
    }

    public PointF getPoint(float offset) {
        try {
            return ((CubicBezier) easingType.getConstructor().newInstance()).getPointF(offset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("CubicBezier init error.");
        }
    }

}
