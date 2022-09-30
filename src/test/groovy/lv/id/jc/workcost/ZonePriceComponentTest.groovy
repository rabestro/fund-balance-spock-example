package lv.id.jc.workcost

import lv.id.jc.workscope.Surface
import spock.lang.*

@Title('Project Customer calculates the price of the zone using the cost of materials and work')
@Narrative('''
As the customer of the project 
I want to calculate the price of the zone using the cost of materials and work
So that I be able to calculate the total cost of processing the zone
''')
@Issue('13')
@Subject([ZonePrice, MaterialPrice, WorkPrice])
class ZonePriceComponentTest extends Specification {

    def "should calculate the cost of work and materials for the zone"() {

        given: 'the price of the zone consists of the price of the materials and the cost of work'
        def zonePriceFunction = ZonePrice.of(MaterialPrice.norm(), WorkPrice.of(workPrice))

        when: 'we have a surface to be treated'
        def surface = Stub(Surface) { area() >> surfaceArea }

        then: 'we get accurately calculated price of the zone'
        zonePriceFunction.apply(surface) == expectedPrice

        where:
        workPrice | surfaceArea | expectedPrice | comment
        10        | 0           | 0             | 'zero price for zero area'
        0         | 10          | 100           | 'at zero labor cost, the zone price is equal to the cost of materials'
        10        | 40          | 800           | 'zone price for one day work'
        10        | 50          | 1050          | 'zone price for two-day work'
    }
}
