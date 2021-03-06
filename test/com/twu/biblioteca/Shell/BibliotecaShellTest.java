package com.twu.biblioteca.Shell;

import com.twu.biblioteca.Core.BibliotecaService;
import com.twu.biblioteca.Resources.MainMenuText;
import com.twu.biblioteca.Router.BibliotecaRouter;
import com.twu.biblioteca.Router.RouterMessage;
import com.twu.biblioteca.Router.RouterState;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BibliotecaShellTest {

    @Test
    public void should_display_welcome_message_when_current_state_is_Initializing() {
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.Initialization, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage(null);

        String expectedResult = "Hello, welcome to the Biblioteca!";
        assertEquals(expectedResult, routerMessage.getText());
    }

    @Test
    public void Should_display_main_menu_when_current_state_is_MainMenu(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.MainMenu, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage(null);

        String expectedResult = MainMenuText.getMainMenuText();
        assertEquals(expectedResult, routerMessage.getText());
    }

    @Test
    public void should_display_book_list_when_current_state_is_MainMenu_and_user_input_is_ListBooks(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.MainMenu, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("1");

        String expectedResult = "----------Book List--------\n"
                              + "BookName: book 1\t\tAuthor: author 1\t\tPublished Year: 2016\n"
                              + "BookName: book 2\t\tAuthor: author 2\t\tPublished Year: 2016\n"
                              + "---------------------------\n";
        assertEquals(expectedResult, routerMessage.getText());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_display_main_menu_when_current_state_is_MainMenu_and_user_input_is_ListBooks_and_continue_execution(){
        BibliotecaService bibliotecaService = new BibliotecaService();
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.MainMenu, bibliotecaService);

        bibliotecaRouter.getRouterMessage("1");
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage(null);

        assertEquals(MainMenuText.getMainMenuText(), routerMessage.getText());
    }

    @Test
    public void should_display_invalid_message_when_user_input_is_not_list_Books_and_current_state_is_MainMenu(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.MainMenu, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("9");

        String expectedResult = "Select a valid option!\n";
        assertEquals(expectedResult, routerMessage.getText());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_display_main_menu_when_user_input_invalid_option_and_current_state_is_MainMenu_and_continue_execution(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.MainMenu, new BibliotecaService());

        bibliotecaRouter.getRouterMessage("1");
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage(null);

        assertEquals(MainMenuText.getMainMenuText(), routerMessage.getText());
    }

    @Test
    public void should_quit_when_user_input_is_Quit_and_current_state_is_MainMenu(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.MainMenu, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("q");

        assertEquals(null, routerMessage.getText());
        assertEquals(true, routerMessage.getExit());
    }

    @Test
    public void should_waiting_for_user_input_when_user_select_Checkout_books_and_current_status_is_MainMenu(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.MainMenu, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("2");

        assertEquals(null, routerMessage.getText());
        assertEquals(true, routerMessage.isWaitingInput());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_display_main_menu_when_user_input_book_name_and_current_state_is_checkout_book_and_continue_execution(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.Checkout, new BibliotecaService());

        bibliotecaRouter.getRouterMessage("book 1");
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage(null);

        assertEquals(MainMenuText.getMainMenuText(), routerMessage.getText());
    }

    @Test
    public void should_display_not_checked_out_books_when_current_state_is_MainMenu_and_user_input_is_ListBooks(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.Checkout, new BibliotecaService());
        bibliotecaRouter.getRouterMessage("book 2");
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("1");

        String expectedResult =  "----------Book List--------\n"
                + "BookName: book 1\t\tAuthor: author 1\t\tPublished Year: 2016\n"
                + "---------------------------\n";
        assertEquals(expectedResult, routerMessage.getText());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_display_successful_message_when_current_state_is_Checkout_and_user_input_book_name_valid(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.Checkout, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("book 1");

        assertEquals("Thank you! Enjoy the book\n\n", routerMessage.getText());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_display_unsuccessful_message_when_current_state_is_Checkout_and_user_input_book_name_not_exist_or_been_checked_out(){
        BibliotecaService bibliotecaService = new BibliotecaService();
        bibliotecaService.getAllBooks().get(0).setIsCheckedOut(true);

        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.Checkout, bibliotecaService);
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("book 1");

        assertEquals("That book is not available.\n\n", routerMessage.getText());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_waiting_for_user_input_when_user_select_ReturnBook_and_current_state_is_MainMenu(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.MainMenu, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("3");

        assertEquals(null, routerMessage.getText());
        assertEquals(true, routerMessage.isWaitingInput());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_display_main_menu_when_user_input_book_name_and_current_state_is_return_book_and_continue_execution(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.ReturnBook, new BibliotecaService());

        bibliotecaRouter.getRouterMessage("book 1");
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage(null);

        assertEquals(MainMenuText.getMainMenuText(), routerMessage.getText());
    }

    @Test
    public void should_display_returned_books_when_current_state_is_MainMenu_and_user_input_is_ListBooks(){
        BibliotecaService bibliotecaService = new BibliotecaService();
        bibliotecaService.getAllBooks().get(0).setIsCheckedOut(true);
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.ReturnBook, bibliotecaService);
        bibliotecaRouter.getRouterMessage("book 1");
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("1");

        String expectedResult = "----------Book List--------\n"
                + "BookName: book 1\t\tAuthor: author 1\t\tPublished Year: 2016\n"
                + "BookName: book 2\t\tAuthor: author 2\t\tPublished Year: 2016\n"
                + "---------------------------\n";
        assertEquals(expectedResult, routerMessage.getText());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_display_successful_message_when_current_state_is_return_book_and_user_input_book_name_exists_and_been_checked_out(){
        BibliotecaService bibliotecaService = new BibliotecaService();
        bibliotecaService.getAllBooks().get(0).setIsCheckedOut(true);
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.ReturnBook, bibliotecaService);

        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("book 1");

        assertEquals("Thank you for returning the book.\n\n", routerMessage.getText());
        assertEquals(false, routerMessage.getExit());
    }

    @Test
    public void should_display_unsuccessful_message_when_current_state_is_return_book_and_user_input_book_name_not_exists_or_not_checked_out(){
        BibliotecaRouter bibliotecaRouter = new BibliotecaRouter(RouterState.ReturnBook, new BibliotecaService());
        RouterMessage routerMessage = bibliotecaRouter.getRouterMessage("book 2");

        assertEquals("That is not a valid book to return.\n\n", routerMessage.getText());
        assertEquals(false, routerMessage.getExit());
    }

}
