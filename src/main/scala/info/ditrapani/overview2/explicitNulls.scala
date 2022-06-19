package info.ditrapani.overview2

extension [T](x: T | Null)
  /** Removes nullability of nullable type
    *
    * nn == not null. Only use on instances guaranteed by construction to be not null.
    */
  inline def nn: T =
    if x == null then throw new NullPointerException("called .nn on null")
    else x

/** Helper function to construct Options from nullables
  *
  * The type signature of Option means the nullability of a type is carride through the Option. But
  * the whole reason Option exists is to handle the nullability. Optionize gives us the correct
  * signature that Option should have had all along.
  */
inline def optionize[T](input: T | Null): Option[T] =
  Option(input.asInstanceOf[T])

extension (str: String)
  inline def trimn: String = str.trim.nn
  inline def splitn(pattern: String, limit: Int): Array[String] =
    str.split(pattern, limit).nn.map { _.nn }
