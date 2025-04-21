package com.example.graphicalpremitivee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CustomDrawingView extends View {

    private Paint paint;

    public CustomDrawingView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        // Draw Top Left Rectangle
        paint.setColor(Color.RED);
        canvas.drawRect(50, 50, 250, 200, paint);

        // Draw Top Right Rectangle
        paint.setColor(Color.BLUE);
        canvas.drawRect(width - 250, 50, width - 50, 200, paint);

        // Draw Center Circle
        paint.setColor(Color.GREEN);
        float radius = 100;
        canvas.drawCircle(width / 2f, height / 2f, radius, paint);

        // Draw Square near center
        paint.setColor(Color.MAGENTA);
        int squareSize = 150;
        canvas.drawRect(width / 2f - squareSize / 2f, height / 2f + 200,
                width / 2f + squareSize / 2f, height / 2f + 200 + squareSize, paint);

        // Draw Line Bottom Left
        paint.setColor(Color.BLACK);
        canvas.drawLine(50, height - 100, 300, height - 100, paint);

        // Draw Line Bottom Right
        canvas.drawLine(width - 300, height - 100, width - 50, height - 100, paint);

        // Draw Signature
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(40);
        canvas.drawText("Made by Garvit", width - 300, height - 40, paint);
    }
}