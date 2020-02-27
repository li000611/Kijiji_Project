package logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entity.Account;
import common.TomcatStartUp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Min Li
 */
class AccountLogicTest {

    private AccountLogic logic;
    private Map<String, String[]> sampleMap;

    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        TomcatStartUp.createTomcat();
    }

    @AfterAll
    final static void tearDownAfterClass() throws Exception {
        TomcatStartUp.stopAndDestroyTomcat();
    }

    @BeforeEach
    final void setUp() throws Exception {
        logic = new AccountLogic();
        /*HashMap implements interface Map. Map is generic, it has two parameters, first is the Key (in our case String) and second is Value (in our case String[])*/
        sampleMap = new HashMap<>();
        /*Map stores date based on the idea of dictionaries. Each value is associated with a key. Key can be used to get a value very quickly*/
        sampleMap.put(AccountLogic.DISPLAY_NAME, new String[]{"Junit 5 Test"});
        /*Map::put is used to store a key and a value inside of a map and Map::get is used to retrieve a value using a key.*/
        sampleMap.put(AccountLogic.USER, new String[]{"junit"});
        /*In this case we are using static values stored in AccoundLogic which represent general names for Account Columns in DB to store values in Map*/
        sampleMap.put(AccountLogic.PASSWORD, new String[]{"junit5"});
        /*This account has Display Name: "Junit 5 Test", User: "junit", and Password: "junit5"*/
    }

    @AfterEach
    final void tearDown() throws Exception {
    }

    @Test
    final void testGetAll() {
        //get all the accounts from the DB
        List<Account> list = logic.getAll();
        //store the size of list/ this way we know how many accounts exits in DB
        int originalSize = list.size();

        //create a new Account and save it so we can delete later
        Account testAccount = logic.createEntity(sampleMap);
        //add the newly created account to DB
        logic.add(testAccount);

        //get all the accounts again
        list = logic.getAll();
        //the new size of accounts must be 1 larger than original size
        assertEquals(originalSize + 1, list.size());

        //delete the new account, so DB is reverted back to it original form
        logic.delete(testAccount);

        //get all accounts again
        list = logic.getAll();
        //the new size of accounts must be same as original size
        assertEquals(originalSize, list.size());
    }

    @Test
    final void testGetWithId() {
        //get all accounts
        List<Account> list = logic.getAll();

        //use the first account in the list as test Account
        Account testAccount = list.get(0);
        //using the id of test account get another account from logic
        Account returnedAccount = logic.getWithId(testAccount.getId());

        //the two accounts (testAcounts and returnedAccounts) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testAccount.getId(), returnedAccount.getId());
        assertEquals(testAccount.getDisplayName(), returnedAccount.getDisplayName());
        assertEquals(testAccount.getUser(), returnedAccount.getUser());
        assertEquals(testAccount.getPassword(), returnedAccount.getPassword());
    }
    
     @Test
    final void testGetAccountWithDisplayName() {
        List<Account> accounts = logic.getAll();
        Account testAc = accounts.get(0);
        Account returnedAc = logic.getAccountWithDisplayName(testAc.getDisplayName());
        assertEquals(testAc.getId(), returnedAc.getId());
        assertEquals(testAc.getUser(), returnedAc.getUser());
        assertEquals(testAc.getDisplayName(), returnedAc.getDisplayName());
        assertEquals(testAc.getPassword(), returnedAc.getPassword());

    }

    @Test
    final void testGetAccountWithUser() {
        List<Account> accounts = logic.getAll();
        Account testAc = accounts.get(0);
        Account returnedAc = logic.getAccountWithUser(testAc.getUser());
        assertEquals(testAc.getId(), returnedAc.getId());
        assertEquals(testAc.getUser(), returnedAc.getUser());
        assertEquals(testAc.getDisplayName(), returnedAc.getDisplayName());
        assertEquals(testAc.getPassword(), returnedAc.getPassword());
    }

    @Test
    final void testGetAccountsWithPassword() {
        List<Account> accounts = logic.getAll();
        Account testAc = accounts.get(0);
        List<Account> returnedAc = logic.getAccountsWithPassword(testAc.getPassword());
        for (Account account : returnedAc){
            assertEquals(account.getPassword(),testAc.getPassword()) ;
        }
        
    }

    @Test
    final void testGetAccountWith() {
        List<Account> accounts = logic.getAll();
        Account testAc = accounts.get(0);
        Account returnedAc = logic.getAccountWith(testAc.getUser(), testAc.getPassword());
        assertEquals(testAc.getId(), returnedAc.getId());
        assertEquals(testAc.getUser(), returnedAc.getUser());
        assertEquals(testAc.getDisplayName(), returnedAc.getDisplayName());
        assertEquals(testAc.getPassword(), returnedAc.getPassword());
    }

    @Test
    final void testSearch() {
        List<Account> accounts = logic.getAll();
        Account testAc = accounts.get(0);
        String search = testAc.getDisplayName().substring(3);
        List<Account> returned = logic.search(search);
        for (Account account : returned) {
            Assertions.assertTrue(account.getDisplayName().contains(search) || account.getUser().contains(search));
        }
    }

    final void testCreateEntity() {
        Account account = logic.createEntity(sampleMap);
        assertEquals(account.getUser(), sampleMap.get(AccountLogic.USER));
        assertEquals(account.getDisplayName(), sampleMap.get(AccountLogic.DISPLAY_NAME));
        assertEquals(account.getPassword(), sampleMap.get(AccountLogic.PASSWORD));
    }

    @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        List<?> hardCodedList = Arrays.asList("ID", "Display Name", "User", "Password");
        assertIterableEquals(list, hardCodedList);
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        List<?> hardCodedList = Arrays.asList(AccountLogic.ID, AccountLogic.DISPLAY_NAME, AccountLogic.USER, AccountLogic.PASSWORD);
        assertIterableEquals(list, hardCodedList);
    }

    @Test
    final void testExtractDataAsList() {
        List<Account> accounts = logic.getAll();
        Account testAc = accounts.get(0);
        Account returnedAc = logic.getWithId(testAc.getId());
        List<?> testAcData = logic.extractDataAsList(testAc);
        List<?> returnedAcData = logic.extractDataAsList(returnedAc);
        assertIterableEquals(testAcData, returnedAcData);
    }
}
