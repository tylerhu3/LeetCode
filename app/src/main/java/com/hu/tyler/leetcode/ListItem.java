package com.hu.tyler.leetcode;

/**
 * Created by tyler on 7/3/2017.
 */

public class ListItem {
    private String title;
    private String rank;
    private String description;
    private String example;
    private String solution;

    private String extra;
    public ListItem(String title, String description, String rank)
    {
        this.title = title;
        this.rank = rank;
        this.description = description;
    }

    public ListItem(String title, String rank, String description, String example, String solution, String extra) {
        this.title = title;
        this.rank = rank;
        this.description = description;
        this.example = example;
        this.solution = solution;
        this.extra = extra;
    }

    public String getExample() {
        return example;
    }

    public String getSolution() {
        return solution;
    }


    public String getTitle()
    {
        return title;
    }

    public String getRank()
    {
        return rank;
    }

    public String getDescription()
    {
        return description;
    }

    public String getExtra() {
        return extra;
    }
}
