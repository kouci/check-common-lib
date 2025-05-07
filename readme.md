# Spécification Librairie `check-common-lib`


## Fonctionnalités

1. **Audit automatique**
    - Interception des méthodes
    - Log des paramètres d'entrée et du résultat
    - Activation via propriété : `check-common.audit-enabled=true`

2. **Log du temps d'exécution**
    - Calcul de la durée d'exécution des méthodes interceptées
    - Activation via propriété : `check-common.log-execution-time=true`

3. **Retry léger (opt-in via annotation)**
    - Réessai automatique des méthodes en cas d'échec
    - Fonctionne uniquement sur les méthodes annotées
    - Paramètres : `maxAttempts`, `delayMs`, `include`, `exclude`

## Architecture du projet

- **Nom du module** : `check-common-lib`
- **Type** : Maven (packaging `jar`)
- **Groupe** : `com.tonorganisation`

## Déploiement dans Maven Central

La librairie `check-common-lib` est déployée sur **Maven Central**. Vous pouvez l'ajouter à votre projet en tant que dépendance Maven comme indiqué ci-dessous.

### Installation Maven

Pour ajouter `check-common-lib` à votre projet, ajoutez la dépendance suivante dans votre fichier `pom.xml` :

```xml
<dependency>
    <groupId>com.tonorganisation</groupId>
    <artifactId>check-common-lib</artifactId>
    <version>1.0.4</version>
</dependency>


