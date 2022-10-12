package lv.id.jc.workcost

import lv.id.jc.workscope.Surface
import spock.lang.*


@Title('Project Customer calculates the price of the zone')
@Narrative('''
As the customer of the project 
I want to calculate all costs per zone
So that I be able to calculate the price of processing the zone
''')
@Issue('13')
@Subject(ZonePrice)
class ZonePriceTest extends PriceSpecification {

    def "should return zero if there is no any processing cost"() {

        given: 'surface for processing with a given area'
        surface.area() >> zoneArea

        when: 'we do not have any costs associated with the processing of the zone'
        def zonePrice = ZonePrice.of()

        then: 'we always get zero cost of work'
        zonePrice.apply(surface) == 0

        where: 'the surface area of the zone can be of any size'
        zoneArea << [0, 1, 100, 1_000_000, Integer.MAX_VALUE]
    }

    def "should calculate the costs for the zone with one item of expenditure"() {

        given: 'surface for processing with a given area'
        surface.area() >> zoneArea

        and: 'one item of expenses for processing the zone'
        def expenditure = Stub(PriceFunction) {
            apply(surface) >> expenses
        }

        when: 'zone treatment price consists of only one expense item'
        def zonePrice = ZonePrice.of(expenditure)

        then: 'we always get zero cost of work'
        zonePrice.apply(surface) == expenses

        where: 'the surface area of the zone can be of any size'
        zoneArea | expenses
        0        | 0
        10       | 0
        10       | 22.5
        1000     | 99.9
    }

    def "should calculate the costs for the zone with several items of expenditure"() {

        given: 'surface for processing with a given area'
        surface.area() >> zoneArea

        and: 'several items of expenses for processing the zone'
        def expenditure = expenses.collect({ cost ->
            Stub(PriceFunction) { apply(surface) >> cost }
        }) as PriceFunction[]

        when: 'zone treatment price consists of all expenses'
        def zonePrice = ZonePrice.of(expenditure)

        then: 'we get an accurately calculated zone treatment price'
        zonePrice.apply(surface) == price

        where:
        zoneArea | expenses           | price
        0        | []                 | 0
        10       | [5, 10]            | 15
        10       | [22.5, 13, 27, 14] | 76.5
        90       | [99.9, 0, 100.1]   | 200
        120      | [0.1, 0.1, 0.1]    | 0.3
    }
}
