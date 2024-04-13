package net.jayugg.leanclass.base;

public class SkillCooldownHelper {
    private final int chargeTime;
    private final int maxCharges;

    public SkillCooldownHelper(int chargeTime, int maxCharges) {
        this.chargeTime = chargeTime;
        this.maxCharges = maxCharges;
    }

    public boolean useSkill(long worldTime, long lastUsedTime) {
        int charges = getCharges(worldTime, lastUsedTime);
        return charges > 0;
    }

    public int getCharges(long worldTime, long lastUsedTime) {
        int timeSinceLastUse = (int) (worldTime - lastUsedTime);
        int charges = timeSinceLastUse / chargeTime;
        return Math.min(charges, maxCharges);
    }

    public int getTimeToNextCharge(long worldTime, long lastUsedTime) {
        int timeSinceLastUse = (int) (worldTime - lastUsedTime);
        return chargeTime - (timeSinceLastUse % chargeTime);
    }

    public boolean isOnCooldown(long worldTime, long lastUsedTime) {
        return getCharges(worldTime, lastUsedTime) <= 0;
    }

}