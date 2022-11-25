module ru.kpfu.itis.selyantsev.likesnakebutbetter {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.kpfu.itis.selyantsev.likesnakebutbetter to javafx.fxml;
    exports ru.kpfu.itis.selyantsev.likesnakebutbetter;
}