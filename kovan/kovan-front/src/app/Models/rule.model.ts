import {Statement} from './statement.model'
import {Parameter} from './parameter.model'
export class Rule {
    commandId:number;
    commandName: string;
    variables : Parameter[];
    statements : Statement[];
    type : number;
}
