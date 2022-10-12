package lv.id.jc.workcost

import lv.id.jc.workscope.Surface
import spock.lang.*


@Title('Project Customer calculates the price of the work')
@Narrative('''
As the customer of the project 
I want to calculate the price of the work for the surface
So that I be able to calculate the total cost of processing the zone
''')
@Issue('10')
@Subject(WorkPrice)
class WorkPriceTest extends PriceSpecification {

    def "should calculate the price of work for the surface"() {

        given: 'surface for processing with a given area'
        surface.area() >> surfaceArea

        when: 'we create a function for calculating the cost of work'
        def priceFunction = WorkPrice.of(workPrice)

        then: 'we get accurately calculated the cost of the work'
        priceFunction.apply(surface) == expectedPrice

        where: 'the cost of processing a unit area, the surface area and expected price'
        workPrice | surfaceArea || expectedPrice
        0         | 10          || 0
        10        | 0           || 0
        1         | 1           || 1
        10        | 40          || 400
        10        | 50          || 550
        3         | 10.1        || 30.3
    }
}
