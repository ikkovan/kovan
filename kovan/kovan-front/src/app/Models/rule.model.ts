import {Statement} from './statement.model'
import {Parameter} from './parameter.model'
export class Rule {
    commandId:number;
    rawCommand: string;
    variables : Parameter[];
    statements : Statement[];
}
