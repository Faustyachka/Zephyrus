package com.zephyrus.wind.workflow;


/**
 * Wrapper of exceptions, occured in zephyrus.wind.workflow packet
 * @author Igor Litvinenko
 */
public class WorkflowException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new instance of <code>WorkflowException</code> without detail message.
     */
    public WorkflowException() {
    }


    /**
     * Constructs an instance of <code>WorkflowException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public WorkflowException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>WorkflowException</code> with the specified detail message
     * and inner exception
     * @param msg the detail message.
     * @param innerException inner exception
     */
    public WorkflowException(String msg, Exception innerException) {
        super(msg, innerException);
    }
}
