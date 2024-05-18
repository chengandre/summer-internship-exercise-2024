package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(expected, result);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(expected, result);
  }

  @Test
  public void PersonMultipleChildrenTest() {
    Person person = new Person(
        "Emily",
        'F',
        new Person[]{
                      new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
                      new Person("James",'M', null, LocalDateTime.of(1045, 1, 1, 0, 0)),
                      new Person("Michael",'M', null, LocalDateTime.of(1044, 1, 1, 0, 0))
                    },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "mother of Michael";
    assertEquals(expected, result);
  }

  @Test
  public void PersonMultipleGrandChildrenTest() {
    Person p11 = new Person("Emma",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0));
    Person p12 = new Person("Olivia",'F', null, LocalDateTime.of(1047, 1, 1, 0, 0));
    Person p21 = new Person("Olivia",'F', null, LocalDateTime.of(1045, 1, 1, 0, 0));

    Person p1 = new Person("Holy", 'F', new Person[]{ p11, p12 }, LocalDateTime.of(1046, 1, 1, 0, 0));
    Person p2 = new Person("James", 'M', new Person[]{ p21 }, LocalDateTime.of(1045, 1, 1, 0, 0));
    Person p3 = new Person("Michael", 'M', null, LocalDateTime.of(1044, 1, 1, 0, 0));

    Person person = new Person("Emily", 'F', new Person[]{ p1, p2, p3 }, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandmother of Olivia";
    assertEquals(expected, result);
  }

  @Test
  public void PersonOneGreatGreatGrandChildrenTest() {
    Person p2111 = new Person("Isabella",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0));

    Person p211 = new Person("Sophia",'F', new Person[] { p2111 }, LocalDateTime.of(1046, 1, 1, 0, 0));

    Person p11 = new Person("Emma",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0));
    Person p12 = new Person("Olivia",'F', null, LocalDateTime.of(1047, 1, 1, 0, 0));
    Person p21 = new Person("Ava",'F', new Person[] { p211 }, LocalDateTime.of(1045, 1, 1, 0, 0));

    Person p1 = new Person("Holy", 'F', new Person[]{ p11, p12 }, LocalDateTime.of(1046, 1, 1, 0, 0));
    Person p2 = new Person("James", 'M', new Person[]{ p21 }, LocalDateTime.of(1045, 1, 1, 0, 0));
    Person p3 = new Person("Michael", 'M', null, LocalDateTime.of(1044, 1, 1, 0, 0));

    Person person = new Person("Emily", 'F', new Person[]{ p1, p2, p3 }, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-grandmother of Isabella";
    assertEquals(expected, result);
  }

  @Test
  public void PersonOneChildOlderThanGrandChild() {
    Person p11 = new Person("Emma",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0));

    Person p1 = new Person("Holy", 'F', new Person[]{ p11 }, LocalDateTime.of(1046, 1, 1, 0, 0));
    Person p2 = new Person("James", 'M', null, LocalDateTime.of(1045, 1, 1, 0, 0));

    Person person = new Person("Emily", 'F', new Person[]{ p1, p2 }, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandmother of Emma";
    assertEquals(expected, result);
  }

  @Test
  public void LargerFamilyTree() {
    Person p11 = new Person("Emma",'F', null, LocalDateTime.of(1019, 1, 1, 0, 0));
    Person p12 = new Person("Olivia",'F', null, LocalDateTime.of(1018, 1, 1, 0, 0));
    Person p13 = new Person("Mia",'F', null, LocalDateTime.of(1017, 1, 1, 0, 0));
    Person p14 = new Person("Charlotte",'F', null, LocalDateTime.of(1016, 1, 1, 0, 0));
    Person p1 = new Person("Holy", 'F', new Person[]{ p11, p12, p13, p14 }, LocalDateTime.of(1015, 1, 1, 0, 0));

    Person p211111 = new Person("Ethan",'M', null, LocalDateTime.of(1014, 1, 1, 0, 0));
    Person p211112 = new Person("Mason",'M', null, LocalDateTime.of(1013, 1, 1, 0, 0));
    Person p21111 = new Person("Liam",'M', new Person[] { p211111, p211112 }, LocalDateTime.of(1012, 1, 1, 0, 0));
    Person p21112 = new Person("Noah",'M', null, LocalDateTime.of(1011, 1, 1, 0, 0));
    Person p2111 = new Person("Isabella",'F', new Person[] { p21111, p21112 }, LocalDateTime.of(1010, 1, 1, 0, 0));
    Person p211 = new Person("Sophia",'F', new Person[] { p2111 }, LocalDateTime.of(1009, 1, 1, 0, 0));
    Person p21 = new Person("Ava",'F', new Person[] { p211 }, LocalDateTime.of(1008, 1, 1, 0, 0));
    Person p22 = new Person("Mia",'F', null, LocalDateTime.of(1007, 1, 1, 0, 0));
    Person p23 = new Person("Charlotte",'F', null, LocalDateTime.of(1006, 1, 1, 0, 0));
    Person p2 = new Person("James", 'M', new Person[]{ p21, p22, p23 }, LocalDateTime.of(1045, 1, 1, 0, 0));

    Person p31 = new Person("Benjamin",'M', null, LocalDateTime.of(1005, 1, 1, 0, 0));
    Person p32 = new Person("Jacob",'M', null, LocalDateTime.of(1004, 1, 1, 0, 0));
    Person p33 = new Person("William",'M', null, LocalDateTime.of(1003, 1, 1, 0, 0));
    Person p34 = new Person("Elijah",'M', null, LocalDateTime.of(1002, 1, 1, 0, 0));
    Person p3 = new Person("Michael", 'M', new Person[]{ p31, p32, p33, p34 }, LocalDateTime.of(1001, 1, 1, 0, 0));

    Person person = new Person("Emily", 'F', new Person[]{ p1, p2, p3 }, LocalDateTime.of(1000, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-great-great-grandmother of Mason";
    assertEquals(expected, result);
  }
}