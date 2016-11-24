package com.sola.github.wow.animations;

import android.graphics.Color;
import android.view.View;

import com.sola.github.wow.colors.ColorChangeHelper;
import com.sola.github.wow.colors.ColorChangeType;
import com.sola.github.wow.eases.EaseType;

/**
 * animation to change the color of background
 */

public class WoWBackgroundColorAnimation extends PageAnimation {

    private EaseType easeType;
    private boolean useSameEaseTypeBack = true;

    private ColorChangeType colorChangeType;

    private int targetColor;
    private int fromColor;

    private int targetA = -1;
    private int targetR = -1;
    private int targetG = -1;
    private int targetB = -1;
    private int fromA = -1;
    private int fromR = -1;
    private int fromG = -1;
    private int fromB = -1;

    private float[] fromHsvVector = null;
    private float[] targetHsvVector = null;

    /**
     * @param page                animation starting page
     * @param startOffset         animation starting offset
     * @param endOffset           animation ending offset
     * @param fromColor           original color
     * @param targetColor         target color
     * @param colorChangeType     how to change the color.
     *                            For more information, please check the ColorChangeType.class
     * @param easeType            ease type.
     *                            For more information, please check the EaseType.class
     * @param useSameEaseTypeBack whether use the same ease type to back
     */
    public WoWBackgroundColorAnimation(
            int page,
            float startOffset,
            float endOffset,
            int fromColor,
            int targetColor,
            ColorChangeType colorChangeType,
            EaseType easeType,
            boolean useSameEaseTypeBack) {

        setPage(page);
        setStartOffset(startOffset);
        setEndOffset(endOffset);

        this.easeType = easeType;
        this.useSameEaseTypeBack = useSameEaseTypeBack;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    private float lastPositionOffset = -1;

    private boolean lastTimeIsExceed = false;
    private boolean lastTimeIsLess = false;

    @Override
    public void play(View onView, float positionOffset) {

        // if the positionOffset is less than the starting color,
        // we should set onView to starting color
        // otherwise there may be offsets between starting color and actually color
        // if the last time we do this, just return
        if (positionOffset <= getStartOffset()) {
            if (lastTimeIsLess) return;
            onView.setBackgroundColor(fromColor);
            lastTimeIsLess = true;
            return;
        }
        lastTimeIsLess = false;

        // if the positionOffset exceeds the endOffset,
        // we should set onView to target color
        // otherwise there may be offsets between target color and actually color
        // if the last time we do this, just return
        if (positionOffset >= getEndOffset()) {
            if (lastTimeIsExceed) return;
            onView.setBackgroundColor(targetColor);
            lastTimeIsExceed = true;
            return;
        }
        lastTimeIsExceed = false;

        // get the true offset
        positionOffset = (positionOffset - getStartOffset()) / (getEndOffset() - getStartOffset());
        float movementOffset;

        if (lastPositionOffset == -1) {
            // first movement
            movementOffset = easeType.getOffset(positionOffset);
        } else {
            if (positionOffset < lastPositionOffset) {
                // back
                if (useSameEaseTypeBack) {
                    movementOffset = 1 - easeType.getOffset(1 - positionOffset);
                } else {
                    movementOffset = easeType.getOffset(positionOffset);
                }
            } else {
                // forward
                movementOffset = easeType.getOffset(positionOffset);
            }
        }
        lastPositionOffset = positionOffset;

        if (colorChangeType == ColorChangeType.RGB) {
            onView.setBackgroundColor(
                    Color.argb(
                            fromA + (int) ((targetA - fromA) * movementOffset),
                            fromR + (int) ((targetR - fromR) * movementOffset),
                            fromG + (int) ((targetG - fromG) * movementOffset),
                            fromB + (int) ((targetB - fromB) * movementOffset))
            );
        } else {
            onView.setBackgroundColor(
                    ColorChangeHelper.getInstance().getHSVColor(
                            fromHsvVector, targetHsvVector, movementOffset));
        }
    }

    private void setARGBandHSV() {
        targetA = Color.alpha(targetColor);
        targetR = Color.red(targetColor);
        targetG = Color.green(targetColor);
        targetB = Color.blue(targetColor);

        fromA = Color.alpha(fromColor);
        fromR = Color.red(fromColor);
        fromG = Color.green(fromColor);
        fromB = Color.blue(fromColor);

        fromHsvVector = ColorChangeHelper.getInstance().toHsvVector(fromColor);
        targetHsvVector = ColorChangeHelper.getInstance().toHsvVector(targetColor);
    }

}
