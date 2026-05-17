package com.rishabhtech.dp.solid.lsp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
class Rectange {
    protected double height;
    protected double width;

    public double area() {
        return width * height;
    }
}

@Setter
class Square extends Rectange {

    protected double size;

    public Square(double size) {
        super(size, size);
    }

    @Override
    public void setWidth(double width)
    {
        this.size = width;
    }

    @Override
    public void setHeight(double height)
    {
        this.height = height;
    }
}

public class Shapes {
    public static void main(String[] args) {
        Square square = new Square(5);
        square.setWidth(5);
        System.out.println(square.area());

        Rectange rectange = new Rectange(5, 3);
        System.out.println(rectange.area());
    }
}
