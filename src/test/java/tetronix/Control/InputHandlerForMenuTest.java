package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tetronix.Model.Menu;
import tetronix.Model.MenuState;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerForMenuTest {

    @Mock
    private Menu mockMenu;

    private InputHandlerForMenu inputHandlerForMenu;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        inputHandlerForMenu = new InputHandlerForMenu(mockMenu);
    }


}
