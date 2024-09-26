package com.company;

public class Main {

    public static void main(String[] strings)
    {
        Model model = new Model();
        Presentation presentation = new Presentation(model);
        Vew vew = new Vew(presentation);
        presentation.associateVue(vew);
    }
}
