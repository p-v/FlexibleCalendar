package com.p_v.flexiblecalendar.view;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author p-v
 */
public interface IDateCellViewDrawer extends ICellViewDrawer {
    /**
     * Date Cell view
     *
     * @param position
     * @param convertView
     * @param parent
     * @param isWithinCurrentMonth
     * @return
     */
    BaseCellView getCellView(int position, View convertView, ViewGroup parent,
                             boolean isWithinCurrentMonth);
}
