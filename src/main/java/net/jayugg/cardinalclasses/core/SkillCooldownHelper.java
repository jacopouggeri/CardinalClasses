package net.jayugg.cardinalclasses.core;

public class SkillCooldownHelper {
    private final int chargeTime;
    private final int maxCharges;
    private final int extraCharges;
    private final float chargeTimeMultiplier;

    public SkillCooldownHelper(int chargeTime, int maxCharges) {
        this.chargeTime = chargeTime;
        this.maxCharges = maxCharges;
        this.extraCharges = 1;
        this.chargeTimeMultiplier = 1.0f;
    }

    public SkillCooldownHelper(int chargeTime, int maxCharges, int extraCharges, float chargeTimeMultiplier) {
        this.chargeTime = chargeTime;
        this.maxCharges = maxCharges;
        this.extraCharges = extraCharges;
        this.chargeTimeMultiplier = chargeTimeMultiplier;
    }

    public boolean useSkill(long worldTime, long lastUsedTime, int skillLevel) {
        int charges = getCharges(worldTime, lastUsedTime, skillLevel);
        return charges > 0;
    }

    public int getCharges(long worldTime, long lastUsedTime, int skillLevel) {
        int timeSinceLastUse = (int) (worldTime - lastUsedTime);
        int charges = timeSinceLastUse / (int) (chargeTime * Math.pow(chargeTimeMultiplier, skillLevel - 1));
        return Math.min(charges, getMaxCharges(skillLevel));
    }

    public int getTimeToNextCharge(long worldTime, long lastUsedTime) {
        int timeSinceLastUse = (int) (worldTime - lastUsedTime);
        return chargeTime - (timeSinceLastUse % chargeTime);
    }

    public int getChargeTime(int skillLevel) {
        return (int) (chargeTime * Math.pow(chargeTimeMultiplier, skillLevel - 1));
    }

    public boolean isOnCooldown(long worldTime, long lastUsedTime, int skillLevel) {
        return getCharges(worldTime, lastUsedTime, skillLevel) <= 0;
    }

    public int getMaxCharges(int skillLevel) {
        return maxCharges + extraCharges * (skillLevel - 1);
    }
}