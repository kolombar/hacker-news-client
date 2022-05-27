package cz.cvut.fit.oop.hackernews
package repositories

trait Repository[ID, T] {
  /**
   * Gets all elements stored in repository
   * @return list of all elements stored in repository
   */
  def get(): List[T]

  /**
   * Gets element specified by its ID
   * @param id unique identificator
   * @return elements specified by its ID (None if element with such ID is not stored)
   */
  def get(id: ID): Option[T]

  /**
   * Stores the given entity to the repository
   * @param entity entity to store
   */
  def save(entity: T): Unit

  /**
   * Clears the repository (removes all stored elements)
   */
  def clear(): Unit

  /**
   * Removes elements specified by its ID
   * @param id unique identificator
   */
  def remove(id: ID): Unit
}
