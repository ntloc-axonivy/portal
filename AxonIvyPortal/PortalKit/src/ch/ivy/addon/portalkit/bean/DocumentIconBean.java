package ch.ivy.addon.portalkit.bean;

import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.ALLOWED_EXCEL_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.ALLOWED_PDF_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.ALLOWED_WORD_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.ARCHIVE_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.CSV_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.EMAIL_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.IMAGE_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.POWER_POINT_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.TEXT_FORMAT;
import static ch.ivy.addon.portalkit.document.DocumentExtensionConstants.XML_FORMAT;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

@ManagedBean
@SessionScoped
public class DocumentIconBean {
  /**
   * DocumentIconBean detects icon class base on Ivy Icons.
   */

  private static final String PREFIX = "icon ivyicon-office-file-";
  private static final String SUFFIX = "-1";

  // Known extensions
  private static final String WORD = "doc";
  private static final String EXCEL = "xls";
  private static final String PDF = "pdf";
  private static final String POWER_POINT = "ppt";
  private static final String TEXT = "txt";
  private static final String CSV = "icon ivyicon-file-csv";
  private static final String ARCHIVE = "icon ivyicon-file-zip";
  private static final String XML = "icon ivyicon-file-xml";
  private static final String IMAGE = "icon ivyicon-image-file-landscape";
  private static final String EMAIL = "icon ivyicon-email-action-unread";
  private static final String DEFAULT = "icon ivyicon-common-file-empty";

  /**
   * Base on extension of document, getIconCssClass method detects a corresponding ivy icon.
   * 
   * @param documentName is a name of file
   * @return return css class for icon
   */
  public static String getIconCssClass(String documentName) {
    String iconClass = StringUtils.EMPTY;
    if (StringUtils.isNotEmpty(documentName)) {
      String fileExtension = FilenameUtils.getExtension(StringUtils.lowerCase(documentName));
      if (ALLOWED_WORD_FORMAT.contains(fileExtension)) {
        iconClass = WORD;
      } else if (ALLOWED_EXCEL_FORMAT.contains(fileExtension)) {
        iconClass = EXCEL;
      } else if (ALLOWED_PDF_FORMAT.contains(fileExtension)) {
        iconClass = PDF;
      } else if (POWER_POINT_FORMAT.contains(fileExtension)) {
        iconClass = POWER_POINT;
      } else if (TEXT_FORMAT.contains(fileExtension)) {
        iconClass = TEXT;
      } else if (ARCHIVE_FORMAT.contains(fileExtension)) {
        return ARCHIVE;
      } else if (XML_FORMAT.contains(fileExtension)) {
        return XML;
      } else if (CSV_FORMAT.contains(fileExtension)) {
        return CSV;
      } else if (IMAGE_FORMAT.contains(fileExtension)) {
        return IMAGE;
      } else if (EMAIL_FORMAT.contains(fileExtension)) {
        return EMAIL;
      } else {
        return DEFAULT;
      }
      return PREFIX + iconClass + SUFFIX;
    }
    return iconClass;
  }

}
