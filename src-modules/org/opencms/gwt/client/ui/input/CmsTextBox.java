/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/gwt/client/ui/input/Attic/CmsTextBox.java,v $
 * Date   : $Date: 2010/06/01 08:21:17 $
 * Version: $Revision: 1.13 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (C) 2002 - 2009 Alkacon Software (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.gwt.client.ui.input;

import org.opencms.gwt.client.I_CmsHasInit;
import org.opencms.gwt.client.ui.css.I_CmsInputCss;
import org.opencms.gwt.client.ui.css.I_CmsInputLayoutBundle;
import org.opencms.gwt.client.ui.css.I_CmsLayoutBundle;
import org.opencms.gwt.client.ui.input.form.CmsWidgetFactoryRegistry;
import org.opencms.gwt.client.ui.input.form.I_CmsFormWidgetFactory;

import java.util.Map;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Basic text box class for forms.
 * 
 * @author Georg Westenberger
 * 
 * @version $Revision: 1.13 $
 * 
 * @since 8.0.0
 * 
 */
public class CmsTextBox extends Composite
implements I_CmsFormWidget, I_CmsHasInit, HasBlurHandlers, HasValueChangeHandlers<String> {

    /** The CSS bundle used for this widget. */
    public static final I_CmsInputCss CSS = I_CmsInputLayoutBundle.INSTANCE.inputCss();

    /** The widget type identifier for this widget. */
    public static final String WIDGET_TYPE = "string";

    /** Default pseudo-padding for text boxes. */
    private static final int DEFAULT_PADDING = 4;

    /** The text box used internally by this widget. */
    protected TextBox m_textbox = new TextBox();

    /** The error display for this widget. */
    private CmsErrorWidget m_error = new CmsErrorWidget();

    //    /** The horizontal "padding" for the text box. */
    //    private int m_paddingX;

    /** The container for the textbox container and error widget. */
    private FlowPanel m_panel = new FlowPanel();

    /** The container for the text box. */
    private CmsPaddedPanel m_textboxContainer = new CmsPaddedPanel(DEFAULT_PADDING);

    /**
     * Constructs a new instance of this widget.
     */
    public CmsTextBox() {

        m_textbox.setStyleName(CSS.textBox());
        m_textboxContainer.setStyleName(CSS.textBoxPanel());
        m_textboxContainer.addStyleName(I_CmsLayoutBundle.INSTANCE.generalCss().cornerAll());
        m_textboxContainer.addStyleName(I_CmsLayoutBundle.INSTANCE.generalCss().textMedium());
        m_panel.add(m_textboxContainer);
        m_panel.add(m_error);
        m_textboxContainer.add(m_textbox);
        m_textboxContainer.setPaddingX(4);
        initWidget(m_panel);
        m_textbox.addValueChangeHandler(new ValueChangeHandler<String>() {

            /**
             * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(ValueChangeEvent event) 
             */
            public void onValueChange(ValueChangeEvent<String> event) {

                fireValueChangedEvent();
            }
        });
    }

    /**
     * Initializes this class.<p>
     */
    public static void initClass() {

        // registers a factory for creating new instances of this widget
        CmsWidgetFactoryRegistry.instance().registerFactory(WIDGET_TYPE, new I_CmsFormWidgetFactory() {

            /**
             * @see org.opencms.gwt.client.ui.input.form.I_CmsFormWidgetFactory#createWidget(java.util.Map)
             */
            public I_CmsFormWidget createWidget(Map<String, String> widgetParams) {

                return new CmsTextBox();
            }
        });
    }

    /**
     * @see com.google.gwt.event.dom.client.HasBlurHandlers#addBlurHandler(com.google.gwt.event.dom.client.BlurHandler)
     */
    public HandlerRegistration addBlurHandler(BlurHandler handler) {

        return m_textbox.addBlurHandler(handler);
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {

        return addHandler(handler, ValueChangeEvent.getType());

    }

    /**
     * @see org.opencms.gwt.client.ui.input.I_CmsFormWidget#getFieldType()
     */
    public FieldType getFieldType() {

        return I_CmsFormWidget.FieldType.STRING;
    }

    /**
     * @see org.opencms.gwt.client.ui.input.I_CmsFormWidget#getFormValue()
     */
    public Object getFormValue() {

        String result = m_textbox.getText();
        if (result.equals("")) {
            result = null;
        }
        return result;
    }

    /**
     * @see org.opencms.gwt.client.ui.input.I_CmsFormWidget#getFormValueAsString()
     */
    public String getFormValueAsString() {

        return (String)getFormValue();
    }

    /**
     * Returns the text in the text box.<p>
     * 
     * @return the text 
     */
    public String getText() {

        return m_textbox.getText();
    }

    /**
     * @see org.opencms.gwt.client.ui.input.I_CmsFormWidget#reset()
     */
    public void reset() {

        m_textbox.setText("");
    }

    /**
     * Sets the changed style on the text box.<p>
     */
    public void setChangedStyle() {

        m_textbox.addStyleName(CSS.changed());
    }

    /**
     * @see org.opencms.gwt.client.ui.input.I_CmsFormWidget#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {

        m_textbox.setEnabled(enabled);
    }

    /**
     * @see org.opencms.gwt.client.ui.input.I_CmsFormWidget#setErrorMessage(java.lang.String)
     */
    public void setErrorMessage(String errorMessage) {

        m_error.setText(errorMessage);
    }

    /**
     * @see org.opencms.gwt.client.ui.input.I_CmsFormWidget#setFormValue(java.lang.Object)
     */
    public void setFormValue(Object value) {

        if (value instanceof String) {
            String strValue = (String)value;
            setText(strValue);
        }
    }

    /**
     * @see org.opencms.gwt.client.ui.input.I_CmsFormWidget#setFormValueAsString(java.lang.String)
     */
    public void setFormValueAsString(String newValue) {

        if (newValue == null) {
            newValue = "";
        }
        setFormValue(newValue);

    }

    /**
     * Sets the text in the text box.<p>
     * 
     * @param text the new text
     */
    public void setText(String text) {

        m_textbox.setText(text);
    }

    /** 
     * Helper method for firing a 'value changed' event.<p>
     */
    protected void fireValueChangedEvent() {

        ValueChangeEvent.fire(this, getText());
    }

}