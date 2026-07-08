module ph.edu.dlsu.lbycpei.quizmaster {
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires com.opencsv;

    exports ph.edu.dlsu.lbycpob.quizmaster;
    exports ph.edu.dlsu.lbycpob.quizmaster.controller;
    exports ph.edu.dlsu.lbycpob.quizmaster.model;
    exports ph.edu.dlsu.lbycpob.quizmaster.view;
    exports ph.edu.dlsu.lbycpob.quizmaster.utils;
}