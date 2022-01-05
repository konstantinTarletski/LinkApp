package lv.bank.cards.core.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataIntegrityException extends Exception {

	private static final long serialVersionUID = 1L;

    public DataIntegrityException(String arg0) {
        super(arg0);
    }

    public DataIntegrityException(Throwable arg0) {
        super(arg0);
    }

    public DataIntegrityException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
