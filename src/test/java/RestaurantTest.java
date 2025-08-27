import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;

    @BeforeEach
    public void setUp() {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spyRestaurant = spy(restaurant);
        LocalTime mockTime = LocalTime.parse("15:00:00"); // Between 10:30 and 22:00
        when(spyRestaurant.getCurrentTime()).thenReturn(mockTime);
        
        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spyRestaurant = spy(restaurant);
        LocalTime mockTime = LocalTime.parse("08:00:00"); // Before 10:30
        when(spyRestaurant.getCurrentTime()).thenReturn(mockTime);
        
        assertFalse(spyRestaurant.isRestaurantOpen());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize+1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        
        assertThrows(itemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>PART 3: ORDER TOTAL CALCULATION<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //The following Test Case will pass
//    @Test
//    public void calculate_order_total_should_return_correct_sum_for_selected_items() {
//        restaurant.addToMenu("Sweet corn soup", 119);
//        restaurant.addToMenu("Vegetable lasagne", 269);
//        restaurant.addToMenu("Sizzling brownie", 319);
//
//        List<String> selectedItems = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
//        int expectedTotal = 119 + 269; // 388
//
//        assertEquals(expectedTotal, restaurant.calculateOrderTotal(selectedItems));
//    }

//    The following test case will fail.
    @Test
    public void calculate_order_total_should_return_correct_sum_for_selected_items() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sizzling brownie", 319);

        List<String> selectedItems = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int expectedTotal = 119 + 273; // 388

        // This will fail initially because calculateOrderTotal method doesn't exist
        assertEquals(expectedTotal, restaurant.calculateOrderTotal(selectedItems));
    }

    @Test
    public void calculate_order_total_should_return_zero_for_empty_order() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        
        List<String> selectedItems = Arrays.asList();
        assertEquals(0, restaurant.calculateOrderTotal(selectedItems));
    }

    @Test
    public void calculate_order_total_should_handle_non_existent_items_gracefully() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        
        List<String> selectedItems = Arrays.asList("Sweet corn soup", "Non-existent item");
        int expectedTotal = 119; // Only valid item counted
        
        assertEquals(expectedTotal, restaurant.calculateOrderTotal(selectedItems));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL CALCULATION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}