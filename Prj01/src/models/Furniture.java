abstract class Furniture {
    private String name;
    private Integer maxSpace, minSpace;

    public String getName() {
        return name;
    }

    public Furniture(String name, Integer area) {
        this.name = name;
        this.maxSpace = area;
        this.minSpace = area;
    }

    public Furniture(String name, Integer area1, Integer area2) {
        this.name = name;
        this.maxSpace = Math.max(area1, area2);
        this.minSpace = Math.min(area1, area2);
    }

    public Integer getMaxSpace() {
        return maxSpace;
    }

    public Integer getMinSpace() {
        return minSpace;
    }
}
