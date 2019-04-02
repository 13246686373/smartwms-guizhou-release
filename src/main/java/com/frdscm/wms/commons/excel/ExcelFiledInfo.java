package com.frdscm.wms.commons.excel;

import java.lang.reflect.Field;

class ExcelFiledInfo implements Comparable<ExcelFiledInfo> {
    private final Field field;
    private final String cellName;
    private final ExcelCellType type;
    private final String format;
    private final String el;
    private final int order;

    ExcelFiledInfo(Field field, ExcelField excelField, int order) {
        this.field = field;
        this.cellName = excelField.value();
        this.type = excelField.type();
        this.format = excelField.format();
        this.el = excelField.el();
        this.order = excelField.order() == 0 ? order : excelField.order();
    }

    @Override
    public int compareTo(ExcelFiledInfo o) {
        int x = this.getOrder();
        int y = o.getOrder();
        return Integer.compare(x, y);
    }

    Field getField() {
        return field;
    }

    String getCellName() {
        return cellName;
    }

    public ExcelCellType getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    String getEl() {
        return el;
    }

    public int getOrder() {
        return order;
    }
}
