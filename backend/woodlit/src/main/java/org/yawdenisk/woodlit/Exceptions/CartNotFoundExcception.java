package org.yawdenisk.woodlit.Exceptions;

public class CartNotFoundExcception extends CustomException {
    @Override
    public String getMessage() {
        return "Cart not found";
    }

    @Override
    public int getCode() {
        return 404;
    }

    @Override
    public String getDescription() {
        return "Cart not found";
    }
}
