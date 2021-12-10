package com.example.n1dispositivosmoveis;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.GnssStatus;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TelaGNSSView extends View {
    private static GnssStatus newStatus;
    private static String opcs;
    private int r;
    private int height,width;




    public TelaGNSSView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public static void onSatelliteStatusChanged(GnssStatus status,String opc) {
        newStatus=status;
        opcs=opc;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // coletando informações do tamanho tela de desenho
        width=getMeasuredWidth();
        height=getMeasuredHeight();

        // definindo o raio da esfera celeste
        if (width<height)
            r=(int)(width/2*0.9);
        else
            r=(int)(height/2*0.9);

        // configurando o pincel para desenhar a projeção da esfera celeste
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);

        // Desenha a projeção da esfera celeste
        // desenhando círculos concêntricos
        int radius=r;
        canvas.drawCircle(computeXc(0), computeYc(0), radius, paint);
        radius=(int)(radius*Math.cos(Math.toRadians(45)));
        canvas.drawCircle(computeXc(0), computeYc(0), radius, paint);
        radius=(int)(radius*Math.cos(Math.toRadians(60)));
        canvas.drawCircle(computeXc(0), computeYc(0), radius, paint);

        //desenhando os eixos
        canvas.drawLine(computeXc(0),computeYc(-r),computeXc(0),computeYc(r),paint);
        canvas.drawLine(computeXc(-r),computeYc(0),computeXc(r),computeYc(0),paint);

        // configura o pincel para desenhar os satélites
        //paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        //contando satélites
        TextView tv_gnss=(TextView)findViewById(R.id.tv_gnssInfo);
        String mens="Dados do Sitema de Posicionamento\n";





        // desenhando os satélites
        if (newStatus!=null) {


            for(int i=0;i<newStatus.getSatelliteCount();i++) {

                if (opcs == "todos") {


                if (newStatus.getCn0DbHz(i) >= 0 && newStatus.getCn0DbHz(i) < 20) {
                    paint.setColor(Color.RED);
                } else {
                    if (newStatus.getCn0DbHz(i) >= 20 && newStatus.getCn0DbHz(i) < 40) {
                        paint.setColor(Color.MAGENTA);
                    } else {
                        if (newStatus.getCn0DbHz(i) >= 40 && newStatus.getCn0DbHz(i) < 60) {

                            paint.setColor(Color.YELLOW);
                        } else {
                            if (newStatus.getCn0DbHz(i) >= 60 && newStatus.getCn0DbHz(i) < 80) {
                                paint.setColor(Color.BLUE);
                            } else {
                                if (newStatus.getCn0DbHz(i) >= 80) {
                                    paint.setColor(Color.GREEN);
                                }
                            }
                        }
                    }

                }

                float az = newStatus.getAzimuthDegrees(i);
                float el = newStatus.getElevationDegrees(i);
                float x = (float) (r * Math.cos(Math.toRadians(el)) * Math.sin(Math.toRadians(az)));
                float y = (float) (r * Math.cos(Math.toRadians(el)) * Math.cos(Math.toRadians(az)));
                canvas.drawCircle(computeXc(x), computeYc(y), 10, paint);

                //canvas.draw;
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(30);
                String satID = newStatus.getSvid(i) + "";
                canvas.drawText(satID, computeXc(x) + 10, computeYc(y) + 10, paint);


            }else{
                Log.i("teste",String.valueOf(newStatus.getConstellationType(i)));
                    Log.i("teste2",opcs);


                    String cons =String.valueOf(newStatus.getConstellationType(i));
                    String ode =opcs;
                    cons.equals(opcs);

                    Log.i("teste2", "tes   "+cons.equals(opcs));


                    if(cons.equals(opcs)){
                        Log.i("teste3","passei");
                        if (newStatus.getCn0DbHz(i) >= 0 && newStatus.getCn0DbHz(i) < 20) {
                            paint.setColor(Color.RED);
                        } else {
                            if (newStatus.getCn0DbHz(i) >= 20 && newStatus.getCn0DbHz(i) < 40) {
                                paint.setColor(Color.MAGENTA);
                            } else {
                                if (newStatus.getCn0DbHz(i) >= 40 && newStatus.getCn0DbHz(i) < 60) {

                                    paint.setColor(Color.YELLOW);
                                } else {
                                    if (newStatus.getCn0DbHz(i) >= 60 && newStatus.getCn0DbHz(i) < 80) {
                                        paint.setColor(Color.BLUE);
                                    } else {
                                        if (newStatus.getCn0DbHz(i) >= 80) {
                                            paint.setColor(Color.GREEN);
                                        }
                                    }
                                }
                            }

                        }

                        float az = newStatus.getAzimuthDegrees(i);
                        float el = newStatus.getElevationDegrees(i);
                        float x = (float) (r * Math.cos(Math.toRadians(el)) * Math.sin(Math.toRadians(az)));
                        float y = (float) (r * Math.cos(Math.toRadians(el)) * Math.cos(Math.toRadians(az)));
                        canvas.drawCircle(computeXc(x), computeYc(y), 10, paint);

                        //canvas.draw;
                        paint.setTextAlign(Paint.Align.LEFT);
                        paint.setTextSize(30);
                        String satID = newStatus.getSvid(i) + "";
                        canvas.drawText(satID, computeXc(x) + 10, computeYc(y) + 10, paint);

                    }else{
                        Log.i("teste3","passei");
                    }

                }
            }
        }
    }
    private int computeXc(double x) {
        return (int)(x+width/2);
    }
    private int computeYc(double y) {
        return (int)(-y+height/2);
    }





}
