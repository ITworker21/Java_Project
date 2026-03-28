package org.example;


public class BMI {

    private double weight;
    private double height;

    public BMI(){}

    public double getHeight() {
        return height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    public void setParams(double w, double h)
    {
        this.height = h;
        this.weight = w;
    }
    public BMI(double w, double h)
    {
        this.height = h;
        this.weight = w;
    }
    public String getBMIType()
    {
        double bmi =8.8;
        String result = "输入有误";
        if(weight > 0 && height >0)
        {
            bmi = weight/(height * height);
            if(bmi < 18.5)
                return "偏瘦";
            else if(bmi < 24)
                return "正常";
            else if(bmi < 28)
                return "偏胖";
            else
                return "肥胖";
        }
        return result;
    }
}
