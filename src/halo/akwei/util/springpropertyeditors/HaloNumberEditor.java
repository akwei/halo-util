package halo.akwei.util.springpropertyeditors;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

public class HaloNumberEditor extends CustomNumberEditor {

	public HaloNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty)
	        throws IllegalArgumentException {
		super(numberClass, allowEmpty);
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		super.setAsText(text);
	}

	@Override
	public String getAsText() {
		// TODO Auto-generated method stub
		return super.getAsText();
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		super.setValue(value);
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return super.getValue();
	}
}
