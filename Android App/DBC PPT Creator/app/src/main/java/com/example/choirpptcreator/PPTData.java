package com.example.choirpptcreator;

import java.util.List;

public class PPTData {
    private boolean does_background_exist;
    private String background_image_path;
    private boolean use_generated_colours;
    private int[] title_colour;
    private int[] content_colour;
    private List<Homepage_ListItem> reading_list;
    private List<Homepage_ListItem> gospel_list;
    private List<Homepage_ListItem> entrance_list;
    private List<Homepage_ListItem> kyrie_list;
    private List<Homepage_ListItem> gloria_list;
    private List<Homepage_ListItem> acclamation_list;
    private List<Homepage_ListItem> offertory_list;
    private List<Homepage_ListItem> holy_list;
    private List<Homepage_ListItem> proclamation_list;
    private List<Homepage_ListItem> communion_list;
    private List<Homepage_ListItem> recessional_list;
    private String main_title, main_subtitle;

    public PPTData(String main_title, String main_subtitle, boolean does_background_exist, String background_image_path, boolean use_generated_colours, int[] title_colour, int[] content_colour, List<Homepage_ListItem> reading_list, List<Homepage_ListItem> gospel_list, List<Homepage_ListItem> entrance_list, List<Homepage_ListItem> kyrie_list, List<Homepage_ListItem> gloria_list, List<Homepage_ListItem> acclamation_list, List<Homepage_ListItem> offertory_list, List<Homepage_ListItem> holy_list, List<Homepage_ListItem> proclamation_list, List<Homepage_ListItem> communion_list, List<Homepage_ListItem> recessional_list){
        this.does_background_exist = does_background_exist;
        this.background_image_path = background_image_path;
        this.use_generated_colours = use_generated_colours;
        this.title_colour = title_colour;
        this.content_colour = content_colour;
        this.reading_list = reading_list;
        this.gospel_list = gospel_list;
        this.entrance_list = entrance_list;
        this.kyrie_list = kyrie_list;
        this.gloria_list = gloria_list;
        this.acclamation_list = acclamation_list;
        this.offertory_list = offertory_list;
        this.holy_list = holy_list;
        this.proclamation_list = proclamation_list;
        this.communion_list = communion_list;
        this.recessional_list = recessional_list;
        this.main_subtitle = main_subtitle;
        this.main_title = main_title;
    }

    public int[] getContent_colour() {
        return content_colour;
    }

    public int[] getTitle_colour() {
        return title_colour;
    }

    public List<Homepage_ListItem> getAcclamation_list() {
        return acclamation_list;
    }

    public List<Homepage_ListItem> getEntrance_list() {
        return entrance_list;
    }

    public List<Homepage_ListItem> getGloria_list() {
        return gloria_list;
    }

    public List<Homepage_ListItem> getGospel_list() {
        return gospel_list;
    }

    public List<Homepage_ListItem> getReading_list() {
        return reading_list;
    }

    public String getBackground_image_path() {
        return background_image_path;
    }

    public List<Homepage_ListItem> getHoly_list() {
        return holy_list;
    }

    public List<Homepage_ListItem> getKyrie_list() {
        return kyrie_list;
    }

    public List<Homepage_ListItem> getCommunion_list() {
        return communion_list;
    }

    public List<Homepage_ListItem> getOffertory_list() {
        return offertory_list;
    }

    public List<Homepage_ListItem> getProclamation_list() {
        return proclamation_list;
    }

    public List<Homepage_ListItem> getRecessional_list() {
        return recessional_list;
    }

    public void setBackground_image_path(String background_image_path) {
        this.background_image_path = background_image_path;
    }

    public void setAcclamation_list(List<Homepage_ListItem> acclamation_list) {
        this.acclamation_list = acclamation_list;
    }

    public void setContent_colour(int[] content_colour) {
        this.content_colour = content_colour;
    }

    public void setDoes_background_exist(boolean does_background_exist) {
        this.does_background_exist = does_background_exist;
    }

    public void setEntrance_list(List<Homepage_ListItem> entrance_list) {
        this.entrance_list = entrance_list;
    }

    public void setGloria_list(List<Homepage_ListItem> gloria_list) {
        this.gloria_list = gloria_list;
    }

    public void setGospel_list(List<Homepage_ListItem> gospel_list) {
        this.gospel_list = gospel_list;
    }

    public void setCommunion_list(List<Homepage_ListItem> communion_list) {
        this.communion_list = communion_list;
    }

    public void setKyrie_list(List<Homepage_ListItem> kyrie_list) {
        this.kyrie_list = kyrie_list;
    }

    public void setOffertory_list(List<Homepage_ListItem> offertory_list) {
        this.offertory_list = offertory_list;
    }

    public void setReading_list(List<Homepage_ListItem> reading_list) {
        this.reading_list = reading_list;
    }

    public void setTitle_colour(int[] title_colour) {
        this.title_colour = title_colour;
    }

    public void setUse_generated_colours(boolean use_generated_colours) {
        this.use_generated_colours = use_generated_colours;
    }

    public void setHoly_list(List<Homepage_ListItem> holy_list) {
        this.holy_list = holy_list;
    }

    public void setProclamation_list(List<Homepage_ListItem> proclamation_list) {
        this.proclamation_list = proclamation_list;
    }

    public void setRecessional_list(List<Homepage_ListItem> recessional_list) {
        this.recessional_list = recessional_list;
    }

    public String getMain_subtitle() {
        return main_subtitle;
    }

    public String getMain_title() {
        return main_title;
    }

    public void setMain_subtitle(String main_subtitle) {
        this.main_subtitle = main_subtitle;
    }

    public void setMain_title(String main_title) {
        this.main_title = main_title;
    }
}
