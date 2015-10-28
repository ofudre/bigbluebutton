package org.bigbluebutton.core.apps

import collection.mutable.Stack
import org.scalatest._
import org.bigbluebutton.core.UnitSpec

class BreakoutRoomsUtilSpec extends UnitSpec {

  it should "return a pdfURL" in {
    val baseURL = "http://localhost/pre1/page1."
    val swfURL = baseURL + "swf"
    val pdfURL = BreakoutRoomsUtil.fromSWFtoPDF(swfURL)
    assert(pdfURL == baseURL + "pdf")
  }

  it should "return a meetingId" in {
    val mainMeetingId = "abc-123"
    val index = 1
    val result = mainMeetingId.concat("-").concat(index.toString())
    val breakoutMeetingId = BreakoutRoomsUtil.createMeetingId(mainMeetingId, index)
    assert(breakoutMeetingId == result)
  }

  it should "return a voiceConfId" in {
    val voiceConfId = "85115"
    val index = 1
    val result = voiceConfId.concat(index.toString())
    val breakoutMeetingId = BreakoutRoomsUtil.createVoiceConfId(voiceConfId, index)
    assert(breakoutMeetingId == result)
  }

  it should "encode the strings properly" in {
    val username = "User 4621018"
    val encodedUsername = "User+4621018"
    val user = BreakoutRoomsUtil.urlEncode(username)
    assert(user == encodedUsername)
  }

  it should "create a join api call" in {
    val baseString = "fullName=User+4621018&isBreakout=true&meetingID=random-1853792&password=mp&redirect=true"

    val params = new collection.mutable.HashMap[String, String]
    params += "fullName" -> BreakoutRoomsUtil.urlEncode("User 4621018")
    params += "isBreakout" -> BreakoutRoomsUtil.urlEncode("true")
    params += "meetingID" -> BreakoutRoomsUtil.urlEncode("random-1853792")
    params += "password" -> BreakoutRoomsUtil.urlEncode("mp")
    params += "redirect" -> BreakoutRoomsUtil.urlEncode("true")

    val result = BreakoutRoomsUtil.createBaseString(params)
    assert(result == baseString)
  }

  it should "calculate the checksum of join url" in {
    val sharedSecret = "a820d30da2db356124fce5bd5d8054b4"
    val checksum = "6baef866df491ae82df992eb14f7f8511d5b77f3"
    val baseString = "fullName=User+4621018&isBreakout=true&meetingID=random-1853792&password=mp&redirect=true"

    val joinChecksum = BreakoutRoomsUtil.calculateChecksum("join", baseString, sharedSecret)
    assert(joinChecksum == checksum)
  }
}

