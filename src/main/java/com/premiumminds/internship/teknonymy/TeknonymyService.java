package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;
import java.util.LinkedList;
import java.util.AbstractMap.SimpleEntry;

class TeknonymyService implements ITeknonymyService {

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    LinkedList<SimpleEntry<Integer, Person>> stack = new LinkedList<>();
    Person currentPerson, targetPerson = person;
    Integer currentLevel, targetLevel = 0;
    String teknonymy;
    SimpleEntry<Integer, Person> currentEntry;

    stack.addFirst(new SimpleEntry<>(0, person));
    while ((currentEntry = stack.pollFirst()) != null) {
      currentPerson = currentEntry.getValue();
      currentLevel = currentEntry.getKey();

      if (currentPerson.children() == null) {
        if (currentLevel > targetLevel || (currentLevel == targetLevel && currentPerson.dateOfBirth().isBefore(targetPerson.dateOfBirth()))) {
          targetPerson = currentPerson;
          targetLevel = currentLevel;
        }
        continue;
      }

      for (Person child : currentPerson.children()) {
        stack.addFirst(new SimpleEntry<>(currentEntry.getKey() + 1, child));
      }
    }

    if (targetLevel == 0) return "";

    teknonymy = person.sex() == 'M' ? "father of " : "mother of ";
    teknonymy += targetPerson.name();

    if (targetLevel > 1) {
      teknonymy = "grand" + teknonymy;
    }

    for (int i = targetLevel - 3; i >= 0; --i) {
      teknonymy = "great-" + teknonymy;
    }

    return teknonymy;
  }
}
