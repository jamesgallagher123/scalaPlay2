package controllers

import javax.inject.Inject

import models.Item
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

import scala.concurrent.Future

class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def listItems = Action { implicit request =>
    Ok(views.html.listItems(Item.items, Item.createItemForm))
  }

  def createItem = Action { implicit request =>

    val formValidationResult = Item.createItemForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listItems(Item.items, formWithErrors))
    }, { item =>
      Item.items.append(item)
      Redirect(routes.Application.listItems)
    })

  }

  def loadDeleteItem = Action { implicit request =>
    Ok(views.html.deleteItem(Item.items, Item.deleteItemForm))
  }

  def deleteItem = Action{ implicit request =>



    Redirect(routes.Application.index())
  }



  //upload image test
  def viewUpload = Action {
    Ok(views.html.imageUpload())
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>
      import java.io.File
      val filename = picture.filename
      val contentType = picture.contentType
      picture.ref.moveTo(new File(s"C:/Users/Administrator/Desktop/pics/$filename"))
      Ok("File uploaded")
    }.getOrElse {
      Redirect(routes.Application.index).flashing(
        "error" -> "Missing file")
    }
  }

}