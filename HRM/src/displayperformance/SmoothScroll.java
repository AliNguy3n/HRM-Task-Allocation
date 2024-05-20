package displayperformance;


import javafx.animation.AnimationTimer;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

/**
* @author Duc Linh
*/
public class SmoothScroll {
    private double targetVvalue = 0;
    private final ScrollPane scrollPane;
    private final double scrollFactor;
    private boolean isUserScrolling = false;

    public SmoothScroll(ScrollPane scrollPane, double scrollFactor) {
        this.scrollPane = scrollPane;
        this.scrollFactor = scrollFactor;

        initSmoothScrolling();
    }

    private void initSmoothScrolling() {
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY() * scrollFactor;
            targetVvalue = Math.max(0, Math.min(1, scrollPane.getVvalue() - deltaY));
            isUserScrolling = true;
            event.consume(); // Consume the event to prevent default scrolling behavior
        });

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double currentVvalue = scrollPane.getVvalue();
                double newVvalue = currentVvalue + (targetVvalue - currentVvalue) * 0.1;
                scrollPane.setVvalue(newVvalue);

                // If the difference is small enough, consider the scrolling done
                if (Math.abs(newVvalue - targetVvalue) < 0.001) {
                    isUserScrolling = false;
                }
            }
        };
        animationTimer.start();
    }
}
