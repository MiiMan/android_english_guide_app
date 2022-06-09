package com.example.a1.bottombar.LoadFile;

public class FileModule {

    public class Section{
        public String text;
        public String subtext;
        public int targetId;


        public String toString() {
            return text + " " + subtext;
        }
    }

    public class Theory{

        class Page {
            String text;
            String subtext;

            public String toString() {
                return text + " / " + subtext;
            }
        }

        private Page[] page;

        public Page[] getPages() {
            return page;
        }

        public String toString() {
            String str = "{";

            for (int i = 0; i < page.length-1; i++) {
                str += "[" + page[i].toString() + "], ";
            }
            return str + "}";
        }
    }

    private String title, subtitle;
    private Section[] section;
    private Theory[] theory;

    public String toString(){
        return title + " " + subtitle;
    }

    public Section[] getSections(){
        return section;
    }

    public Theory[] getTheory(){
        return theory;
    }
}
