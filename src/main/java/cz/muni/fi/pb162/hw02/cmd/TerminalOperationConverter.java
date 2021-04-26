package cz.muni.fi.pb162.hw02.cmd;

import com.beust.jcommander.converters.BaseConverter;
import cz.muni.fi.pb162.hw02.TerminalOperation;

/**
 * Converter for terminal operation command line option
 */
public class TerminalOperationConverter extends BaseConverter<TerminalOperation> {

    /**
     * Constructor for this class
     * @param optionName name of the command line option
     */
    public TerminalOperationConverter(String optionName) {
        super(optionName);
    }

    @Override
    public TerminalOperation convert(String value) {
        return TerminalOperation.forName(value);
    }
}
