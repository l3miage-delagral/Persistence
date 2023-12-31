/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package fr.uga.miage.m1.shapes;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

import fr.uga.miage.m1.persistence.Visitor;
import fr.uga.miage.m1.persistence.Visitable;

/**
 * This inner class implements the triangle <tt>SimpleShape</tt> service.
 * It simply provides a <tt>draw()</tt> that paints a triangle.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
class Triangle implements SimpleShape, Visitable {

    private int mx;

    private int my;

    private Polygon poly;

    private Color color = Color.BLACK;

    public Triangle(int x, int y) {
        mx = x - 25;
        my = y - 25;
        int[] xcoords = { mx + 25, mx, mx + 50 };
        int[] ycoords = {my, my + 50, my + 50 };
        poly = new Polygon(xcoords, ycoords, 3);
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(mx, my, Color.GREEN, (float)mx + 50, my, Color.WHITE);
        g2.setPaint(gradient);
        int[] xcoords = { mx + 25, mx, mx + 50 };
        int[] ycoords = {my, my + 50, my + 50 };
        poly = new Polygon(xcoords, ycoords, 3);
        GeneralPath polygon = new GeneralPath(Path2D.WIND_EVEN_ODD, xcoords.length);
        polygon.moveTo((float)mx + 25, my);
        for (int i = 0; i < xcoords.length; i++) {
            polygon.lineTo(xcoords[i], ycoords[i]);
        }
        polygon.closePath();
        g2.fill(polygon);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(color);
        g2.setStroke(wideStroke);
        g2.draw(polygon);
    }

    @Override
    public Object accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }

    @Override
    public int getX() {
        return this.mx;
    }

    @Override
    public int getY() {
        return this.my;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public String getShapeName() {
        return "triangle";
    }

    @Override
    public boolean contains(int x, int y) {
        return poly.contains(x, y);
    }

    @Override
    public void move(int x, int y) {
        this.mx = x-25;
        this.my = y-25;
    }

    @Override
    public void selected(boolean selected ) {
        if (selected) {
            color = Color.green;
        } else {
            color = Color.BLACK;
        }
    }


    @Override
    public ShapeFactory.Shapes getShapeType() {
        return ShapeFactory.Shapes.TRIANGLE;
    }

}
