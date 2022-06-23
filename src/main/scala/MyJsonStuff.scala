import play.api.libs.json.{JsString, Reads, Writes, __}

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.{Failure, Success, Try}

object MyJsonStuff {

  private val formatter = DateTimeFormatter.ISO_DATE

  implicit lazy val dateReads: Reads[LocalDate] =
    __.read[String].flatMap { string =>
      Try(LocalDate.parse(string, formatter)) match {
        case Success(value) => Reads.pure(value)
        case Failure(_)     => Reads.failed("invalid date format")
      }
    }

  implicit lazy val dateWrites: Writes[LocalDate] =
    Writes { date =>
      JsString(date.format(formatter))
    }
}
