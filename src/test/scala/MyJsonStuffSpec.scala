import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import play.api.libs.json.{JsString, Json}

import java.time.LocalDate

class MyJsonStuffSpec extends AnyFreeSpec with Matchers with OptionValues {

  val date = LocalDate.of(2000, 2, 1)
  val json = JsString("2000-02-01")

  "must be able to convert a date to a json string" in {
    Json.toJson(date)(MyJsonStuff.dateWrites) mustEqual json
  }

  "must be able to convert json into a date successfully" in {
    Json.fromJson[LocalDate](json)(MyJsonStuff.dateReads).asOpt.value mustEqual date
  }

  "must fail to convert invalid json into a date" in {
    Json.fromJson[LocalDate](JsString("foobar"))(MyJsonStuff.dateReads).isError mustBe true
  }

  "must be able to convert json into a date and back again" in {
    val newDate = Json.fromJson[LocalDate](json)(MyJsonStuff.dateReads).asOpt.value
    Json.toJson(newDate)(MyJsonStuff.dateWrites) mustEqual json
  }
}
