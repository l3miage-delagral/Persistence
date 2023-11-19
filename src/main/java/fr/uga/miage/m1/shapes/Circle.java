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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import fr.uga.miage.m1.persistence.Visitor;
import fr.uga.miage.m1.persistence.Visitable;



class Circle implements SimpleShape, Visitable {

    private int mx;

    private int my;

    private Ellipse2D ellipse;

    private Color color = Color.BLACK;

    public Circle(int x, int y) {
        mx = x - 25;
        my = y - 25;
        ellipse = new Ellipse2D.Double(mx, my, 50, 50);
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(mx, my, Color.RED, (float)mx + 50, my, Color.WHITE);
        g2.setPaint(gradient);
        ellipse = new Ellipse2D.Double(mx, my, 50, 50);
        g2.fill(ellipse);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(color);
        g2.setStroke(wideStroke);
        g2.draw(new Ellipse2D.Double(mx, my, 50, 50));
    }

    @Override
    public Object accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }

    public int getX() {
        return mx;
    }

    public int getY() {
        return my;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public String getShapeName() {
        return "circle";
    }

    @Override
    public boolean contains(int x, int y) {
        return ellipse.contains(x, y);
    }

    @Override
    public void move(int dx, int dy) {
        this.mx = dx - 25;
        this.my = dy - 25;
        ellipse = new Ellipse2D.Double(mx, my, 50, 50);
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
    public ShapeFactory.Shapes getShapeType(){
        return ShapeFactory.Shapes.CIRCLE;
    }

}
