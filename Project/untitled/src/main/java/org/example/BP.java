package org.example;

public class BP {
    private int sbp;
    private int dbp;

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public int getSbp() {
        return sbp;
    }
    public void setParams(int s, int d) {
        this.sbp = s;
        this.dbp = d;
    }
    public String getBPType()
    {

        String result = "输入有误";
        if(sbp > 0 && dbp >0)
        {
            if (sbp < 120 && dbp < 80)
                return "正常";
            if(sbp < 139 && dbp <89)
                return "正常高血压";
            if (sbp >= 180 || dbp >= 110)
                return "3级高血压";
            if (sbp >= 160 || dbp > 99)
                return "2级高血压";
            if (sbp >= 140 || dbp >= 90)
                return "1级高血压";

        }
        return result;
    }
}
