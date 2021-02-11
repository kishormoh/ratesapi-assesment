Feature:
  Verify different Get operations

  Scenario: Verify latest rates
    Given I want to get operation for "https://api.ratesapi.io/api"
    When endpoint is "/latest"
