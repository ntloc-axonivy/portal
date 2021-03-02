package ch.ivy.addon.portalkit.persistence.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Objects;

import ch.ivy.addon.portalkit.bo.ExternalLink;

public class ExternalLinkDao extends AbstractDao<ExternalLink> {

  public ExternalLinkDao() {
    super();
  }
  
  public List<ExternalLink> findStartableLink(Long userId) {
    return findAll()
        .stream()
        .filter(externalLink -> Optional.ofNullable(externalLink.getCreatorId()).orElse(-1L).longValue() == userId.longValue() || externalLink.isPublic())
        .collect(Collectors.toList());
  }

  public ExternalLink findExternalLinkByName(String externalLinkName) {
    return findAll()
        .stream()
        .filter(externalLink -> Objects.equal(externalLink.getName(), externalLinkName))
        .findFirst().orElse(null);
  }

  public void delete(long id) {
    ExternalLink persistedExternalLink = findById(id);
    delete(persistedExternalLink);
  }

}
