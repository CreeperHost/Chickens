# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),

## [Unreleased]

## [1.0.25] - 2022.11.04
### Fixed
- Chickens not dropping raw chicken or feathers

## [1.0.24] - 2022.11.03
### Fixed
- Allow JEI categories to be localised
- Allow Chickens tooltips to be localised

## [1.0.23] - 2022.11.02
### Fixed
- Stop input slots on breeder allowing items to be pulled out via automation
## [1.0.22] - 2022.11.02
### Fixed
- Save inLove state to nbt

## [1.0.21] - 2022.11.01
### Fixed
- Gain now also make the roost run faster
- Fix Henhouse dropping items when removed.
- Fix Henhouse not picking up drops
- Fix Henhouse power not syncing

## [1.0.20] - 2022.11.01
### Fixed
- Issue that allowed chickens to inherit stats from other chicken types

## [1.0.19] - 2022-10-19
### Fixed
- Missing texture particle of chicken item

## [1.0.18] - 2022-10-18
### Fixed
- Allow breeder to output to all slots
- Stop roost from creating stacks over maxCount

## [1.0.17] - 2022-10-11
### Fixed
- Breeder not dropping inventory
- Issue with not being able to lookup chicken recipes with nbt


## [1.0.16] - 2022-10-10
### Added
- Add % chance to jei screen

### Fixed
- Henhouse gui background layer


## [1.0.15] - 2022-10-09
### Fixed
- Hoppers being able to insert into wrong slots
- CanRun not checking if the itemstack is correct

## [1.0.14] - 2022-10-08
### Fixed
- Stacksize issues with the Roost
- Not outputting to all 4 output slots

## [1.0.13] - 2022-10-07
### Fixed
- Fixed issue with Roost not having a valid output

## [1.0.12] - 2022-10-06
### Fixed
- Chicken catcher will now work with baby chickens correctly

## [1.0.11] - 2022-10-05
### Fixed
- Not all types of seeds being able to be added via pipes
- Breeder putting new chickens in the wrong slots, closes #18
- Crossed breded chickens getting their parents stats
- Issue that could cause no chicken to be added to the inventory when breeding
