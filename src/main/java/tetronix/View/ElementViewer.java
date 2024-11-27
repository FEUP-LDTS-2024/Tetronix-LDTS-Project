package tetronix.View;


public interface ElementViewer<T> {

    void draw(T element, ScreenManager screenManager);
}
